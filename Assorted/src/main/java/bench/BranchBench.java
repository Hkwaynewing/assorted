package bench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BranchBench {
    @Param({ "0.02", })
    private double bias;

    @Param("10000")
    private int count;

    private final List<Byte> randomZeroOnes = new ArrayList<>(count);


    @Setup
    public void setup() {
        Random r = new Random(12345);

        for (int c = 0; c < count; c++) {
            byte zeroOrOne = (byte) (c < (bias * count) ? 1 : 0);
            randomZeroOnes.add(zeroOrOne);
        }
        Collections.shuffle(randomZeroOnes, r);
    }

    @Benchmark
    public int static_ID_ifElse() {
        int i = 0;
        final List<Byte> rzo = randomZeroOnes;
        for (final Byte zeroOrOne : rzo) {
            if (zeroOrOne == 1) {
                i++;
            } else {
                i--;
            }
        }
        return i;
    }

}
