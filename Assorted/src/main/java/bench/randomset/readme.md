So I was going to start with “why is allocation bad”, but we need to preface this with suitability. i.e. if you are
writing a tool that you run every month for a couple of minutes, it doesn't matter.
But in performance critical code, allocations should be avoided if its reasonable to do so.    
Firstly, allocations cause a cache miss i.e. the data you will use in subsequent instructions are guaranteed to not be
in a register or CPU cache by definition ... because you are allocating a new object.
Java has made great progress with TLABs (Thread Local Allocation Buffers) which allow the JVM to allocate within a
thread, and not contend with other threads allocating. But its still a call into a heap management routine, which has to
do a bunch of work (allocating a block from an arena etc). This involves lots of reads and writes to slow memory. It
might be 10s to 100s of instructions depending on whether an appropriately sized region can be found, whether more
memory needs to be allocated from the OS etc.   
Secondly, allocations need to be cleaned up by the garbage collector. Again, lots of progress with collectors such as
ZGC ... but the old maxim of "the fastest way to do something is not to do it" applies. i.e. if you can reduce
allocation rate, you waste less CPU cycles, reduce context switching etc.

And somewhat related, an allocation is often behind a pointer - which is a another set of memory accesses, which
generally do not benefit from cache line prefetching (i.e. elements of an array are next to each other in memory, and
prefetched 64 bytes at a time). Whereas an object behind a pointer is nearly always random access - it is somewhere in
the heap, and unlikely to be near other allocated data.

So something like `IntArrayListEx.wrap(random.ints(endPageLoc - pageSize, endPageLoc).distinct().limit(directJobsToAdd)
.toArray());` is very heavy in allocation:

1. the `IntArrayListEx` class, along with its member variables (again, separate allocations for the backing array etc)
   and
   class information
2. a stream allocated by the `ints` method
3. comparator state in the `distinct` call
4. a filter/cap in the `limit`
5. then another in the `toArray`

If we benchmark this, we see:

```
RandomSetBenchmark.benchStream                                   thrpt   25    7276268.766 ± 200975.414   ops/s
RandomSetBenchmark.benchStream:·gc.alloc.rate                    thrpt   25       5550.875 ±    153.313  MB/sec
RandomSetBenchmark.benchStream:·gc.alloc.rate.norm               thrpt   25        840.020 ±      0.001    B/op
-> 840 bytes per operation
```

@bhallett’s suggestion is a good one - generate two random numbers as local primitive variables, spin while the second
equals the first, then allocate one two element array and put the values in them. For this, we see:

```
RandomSetBenchmark.benchIntArrayListEx                           thrpt   25   96544276.658 ± 336743.053   ops/s
RandomSetBenchmark.benchIntArrayListEx:·gc.alloc.rate            thrpt   25         ≈ 10⁻⁴               MB/sec
RandomSetBenchmark.benchIntArrayListEx:·gc.alloc.rate.norm       thrpt   25         ≈ 10⁻⁶                 B/op
```

Note that even though its a “list”, contains would actually be faster than a set since the two elements are right next
to each other and prefetched so its just two cmp instructions.
Whereas a set, even with two elements, is still going to hash the key, probe the buckets etc.
Also note that we are not actually allocating … (see more below)
You can see this with:

```
RandomSetBenchmark.benchIntArraySet                              thrpt   25   94245573.250 ± 751906.494   ops/s
RandomSetBenchmark.benchIntArraySet:·gc.alloc.rate               thrpt   25         ≈ 10⁻⁴               MB/sec
RandomSetBenchmark.benchIntArraySet:·gc.alloc.rate.norm          thrpt   25         ≈ 10⁻⁶                 B/op
```

The set (even array set) is slightly slower.

The standard open addressed set is much slower:

```
RandomSetBenchmark.benchIntOpenHashSet                           thrpt   25   58878629.848 ± 537229.094   ops/s
RandomSetBenchmark.benchIntOpenHashSet:·gc.alloc.rate            thrpt   25         ≈ 10⁻⁴               MB/sec
RandomSetBenchmark.benchIntOpenHashSet:·gc.alloc.rate.norm       thrpt   25         ≈ 10⁻⁶                 B/op
```

Note something interesting though the allocation rate has dropped away significantly - this is because the JVM is
performing escape analysis and realising that this locally allocated list / set never leaves the function and can be
stack allocated.

We can go a bit faster (~10%) than a stack allocated integer array though (its doing 96544276.658 ± 336743.053 ops/s,
and that is to use a bit set i.e. we can just set bits in an int to reflect the chosen position and spin on the number
of bits set. These are intrinsics (mapped directly on to CPU instructions) so they are quite quick:

```
RandomSetBenchmark.benchBitsDataDep                      thrpt   25  106019029.134 ± 628325.755   ops/s
RandomSetBenchmark.benchBitsDataDep:·gc.alloc.rate       thrpt   25         ≈ 10⁻⁴               MB/sec
RandomSetBenchmark.benchBitsDataDep:·gc.alloc.rate.norm  thrpt   25         ≈ 10⁻⁶                 B/op
```

This benchmark also uses a modulus operator to bound the random number vs. the hoops a ranged number generator needs to
go through (its just branchless code vs. what the ranged version needs to do)
Branching code is hard for a CPU to optimise - if it speculates incorrectly, the execution pipeline stalls while it
waits for more data or backtracks on computation.

The name of this benchmark gives it away, but there is a data dependency in the benchmark that the JIT doesn’t do a good
job of.
There is a technique called Single Static Assignment i.e. variables are only ever assigned once, and created near to
where they are used: https://en.wikipedia.org/wiki/Static_single_assignment_form
We can go slightly quicker...0.3% in this tiny benchmark (106369824.875 ops/s) by explicitly separating the two random
number calls and combining them i.e. the CPU will run both of these operations in “parallel” (running the computation on
the first while fetching data for the second).

```
RandomSetBenchmark.benchBitsTuned                        thrpt   25  106369824.875 ± 588628.089   ops/s
RandomSetBenchmark.benchBitsTuned:·gc.alloc.rate         thrpt   25         ≈ 10⁻⁴               MB/sec
RandomSetBenchmark.benchBitsTuned:·gc.alloc.rate.norm    thrpt   25         ≈ 10⁻⁶                 B/op
```

You’ll notice this version has less variability than the prior too (because we are less sensitive to pipeline stalls in
one computation vs. another). (edited) 