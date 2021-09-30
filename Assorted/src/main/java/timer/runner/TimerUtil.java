package timer.runner;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * This util class provides abstraction from BaseTimer to facilitate its usage
 * <pre>
 *     While an AOP like design provides a cleaner way, this class provides more flexibility as
 *     (1) It can time Private method and in class method calls
 *     (2) It allows you to do more complicated things with the result by overriding {@link BaseTimer}'s postProcess() method
 *
 *     You should still use the AOP solution (to be provided) if
 *          the method you want to time is a Public/Protected one and
 *          you simply want to log the duration with no other actions
 * </pre>
 */
public class TimerUtil {

    private TimerUtil() {
    }

    /**
     * time a {@code process} that has no return value
     *
     * @param process       the process to time
     * @param timerSupplier timer to use
     */
    public static void runWithTimer(Runnable process, Supplier<BaseTimer> timerSupplier) {
        var timer = timerSupplier.get();
        try (timer) {
            process.run();
        }
    }

    /**
     * time a {@code process} that has return value
     *
     * @param process       the process to time
     * @param timerSupplier timer to use
     * @param <T>           return type of the process
     * @return process's return value
     */
    public static <T> T runWithTimer(Supplier<T> process, Supplier<BaseTimer> timerSupplier) {
        var timer = timerSupplier.get();
        try (timer) {
            return process.get();
        }
    }

    /**
     * time a process with custom {@code timeUnit}
     *
     * @param process       the process to time
     * @param timerSupplier timer to use
     * @param timeUnit      timeUnit to use, default one is Seconds
     */
    public static void runWithTimer(Runnable process, Supplier<BaseTimer> timerSupplier, TimeUnit timeUnit) {
        var timer = timerSupplier.get();
        timer.setTimeUnit(timeUnit);
        try (timer) {
            process.run();
        }
    }

    /**
     * time a process if {@code runCondition} is satisfied
     *
     * @param process       the process to time
     * @param timerSupplier timer to use
     * @param runCondition  condition to do the timing
     */
    public static void runWithTimer(Runnable process, Supplier<BaseTimer> timerSupplier, boolean runCondition) {
        if (runCondition) {
            runWithTimer(process, timerSupplier);
        } else {
            process.run();
        }
    }

    public static Logger getCallerLogger(Class<?> callerClass) {
        return LoggerFactory.getLogger(callerClass);
    }

}
