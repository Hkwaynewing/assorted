package bench;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


/**
 * @see <a href="https://mp.weixin.qq.com/s/36ICuAz1GhA4shyWCKDq4Q">基准测试JMH</a>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(4)
public class BenchArraylist {

    private static final Random random = new Random();
    private static final int NUM = 5000;
    private String[] str = new String[NUM];
    private int[] ints = new int[NUM];

    private String generateRanStr() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Setup
    public void setup() {
        for (int i = 0; i < NUM; i++) {
            str[i] = generateRanStr();
            ints[i] = str[i].hashCode();
        }
    }

    @Benchmark
    public List<String> withoutInitSize() {
        List<String> result = new ArrayList();
        for (int i = 0; i < NUM; i++) {
            result.add(str[i]);
        }
        return result;
    }

    @Benchmark
    public List<String> withInitSize() {
        List<String> result = new ArrayList(NUM);
        for (int i = 0; i < NUM; i++) {
            result.add(str[i]);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(BenchArraylist.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
