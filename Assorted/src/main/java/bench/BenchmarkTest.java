package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @see <a href="https://mp.weixin.qq.com/s/36ICuAz1GhA4shyWCKDq4Q">基准测试JMH</a>
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(4)
public class BenchmarkTest {

    private static final Random random = new Random();
    private static final int NUM = 5000;
    private String[] str = new String[NUM];
    private String[] str2 = new String[NUM];
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
        str2 = Arrays.copyOf(str, NUM);
    }

    @Benchmark
    public long equals() {
        int j = 0;
        for (int i = 0; i < NUM; i++) {
            if (str[i].equals(str2[i])) {
                j++;
            }
        }
        return j;
    }

    @Benchmark
    public long hash() {
        int j = 0;
        for (int i = 0; i < NUM; i++) {
            if (ints[i] == str[i].hashCode()) {
                j++;
            }
        }
        return j;
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(BenchmarkTest.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
