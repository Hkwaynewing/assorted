package bench.randomset;

import java.util.concurrent.ThreadLocalRandom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class RandomSetBenchmark {
    @State(Scope.Thread)
    public static class BenchState {

        private ThreadLocalRandom random;
        private final int endPageLoc = 20;
        private final int pageSize = 20;
        private final int directJobsToAdd = 2;
        private IntArrayListEx iale;
        private IntOpenHashSet iohs;
        private IntArraySet ias;

        @Setup(Level.Trial)
        public void doSetup() {
            random = ThreadLocalRandom.current();
            iale = new IntArrayListEx(2);
            iohs = new IntOpenHashSet();
            ias = new IntArraySet();
        }
    }

    @Benchmark
    public void benchStream(final BenchState state, final Blackhole blackhole) {
        final int endPageLoc = state.endPageLoc;
        final int pageSize = state.pageSize;
        final int directJobsToAdd = state.directJobsToAdd;

        blackhole.consume(IntArrayListEx.wrap(
                state.random.ints(endPageLoc - pageSize, endPageLoc).distinct().limit(directJobsToAdd).toArray()));
    }

    @Benchmark
    public void benchIntArrayListEx(final BenchState state, final Blackhole blackhole) {
        final int endPageLoc = state.endPageLoc;
        final int pageSize = state.pageSize;

        final ThreadLocalRandom random = state.random;
        final int first = random.nextInt(endPageLoc - pageSize, endPageLoc);
        int second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        while (second == first) {
            second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        }
        final IntArrayListEx iale = state.iale;
        iale.clear();
        iale.add(first);
        iale.add(second);

        blackhole.consume(iale);
    }

    @Benchmark
    public void benchIntArraySet(final BenchState state, final Blackhole blackhole) {
        final int endPageLoc = state.endPageLoc;
        final int pageSize = state.pageSize;

        final ThreadLocalRandom random = state.random;
        final int first = random.nextInt(endPageLoc - pageSize, endPageLoc);
        int second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        while (second == first) {
            second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        }
        final IntArraySet set = state.ias;
        set.clear();
        set.add(first);
        set.add(second);

        blackhole.consume(set);
    }

    @Benchmark
    public void benchIntOpenHashSet(final BenchState state, final Blackhole blackhole) {
        final int endPageLoc = state.endPageLoc;
        final int pageSize = state.pageSize;

        final ThreadLocalRandom random = state.random;
        final int first = random.nextInt(endPageLoc - pageSize, endPageLoc);
        int second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        while (second == first) {
            second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        }
        final IntOpenHashSet set = state.iohs;
        set.clear();
        set.add(first);
        set.add(second);

        blackhole.consume(set);
    }


    @Benchmark
    public void benchBits(final BenchState state, final Blackhole blackhole) {
        final int endPageLoc = state.endPageLoc;
        final int pageSize = state.pageSize;

        final ThreadLocalRandom random = state.random;
        int set = (1 << random.nextInt(endPageLoc - pageSize, endPageLoc));
        final int second = random.nextInt(endPageLoc - pageSize, endPageLoc);
        set |= (1 << second);
        while (Integer.bitCount(set) < 2) {
            set |= random.nextInt(endPageLoc - pageSize, endPageLoc);
        }

        final int position1 = Integer.numberOfTrailingZeros(set);
        final int position2 = 31 - Integer.numberOfLeadingZeros(set);

        // Need to combine them so the JVM doesn't just throw away the computation
        // Bitwise AND is fast enough to be ignored
        blackhole.consume(position1 & position2);
    }

    @Benchmark
    public void benchBitsTuned(final BenchState state, final Blackhole blackhole) {
        final int bound = state.pageSize;
        final int endPageLoc = state.endPageLoc;
        final ThreadLocalRandom random = state.random;

        int set = 1 << (random.nextInt() % bound);
        final int second = 1 << (random.nextInt() % bound);
        set |= second;
        while (Integer.bitCount(set) < 2) {
            set |= 1 << (random.nextInt() % bound);
        }

        // Then to find the positions, you add state.endPageLoc to the set bits
        final int position1 = endPageLoc + Integer.numberOfTrailingZeros(set);
        final int position2 = endPageLoc + (31 - Integer.numberOfLeadingZeros(set));

        // Need to combine them so the JVM doesn't just throw away the computation
        // Bitwise AND is fast enough to be ignored
        blackhole.consume(position1 & position2);
    }

    @Benchmark
    public void benchBitsDataDep(final BenchState state, final Blackhole blackhole) {
        final int bound = state.pageSize;
        final int endPageLoc = state.endPageLoc;
        final ThreadLocalRandom random = state.random;

        int set = 1 << (random.nextInt() % bound);
        set |= 1 << (random.nextInt() % bound);
        while (Integer.bitCount(set) < 2) {
            set |= 1 << (random.nextInt() % bound);
        }

        // Then to find the positions, you add state.endPageLoc to the set bits
        final int position1 = endPageLoc + Integer.numberOfTrailingZeros(set);
        final int position2 = endPageLoc + (31 - Integer.numberOfLeadingZeros(set));

        // Need to combine them so the JVM doesn't just throw away the computation
        // Bitwise AND is fast enough to be ignored
        blackhole.consume(position1 & position2);
    }
}
