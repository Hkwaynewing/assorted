package timer.runner;

import timer.LogTimer;

import java.util.function.Supplier;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;
import static timer.runner.TimerUtil.getCallerLogger;

/**
 * This util is provides functions to TIME and LOG a process
 */
public class LogTimerUtil {

    private LogTimerUtil() {
    }

    /**
     * time a {@code process} that has no return value
     *
     * @param process        the process to time
     * @param logMsgSupplier message to log
     */
    public static void runWithLogTimer(Runnable process, Supplier<String> logMsgSupplier) {
        Class<?> callerClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        runWithTimer(process, () -> new LogTimer(logMsgSupplier, getCallerLogger(callerClass)));
    }

    /**
     * time a {@code process} that has return value
     *
     * @param process        the process to time
     * @param logMsgSupplier message to log
     * @param <T>            return type of the process
     * @return process's return value
     */
    public static <T> T runWithLogTimer(Supplier<T> process, Supplier<String> logMsgSupplier) {
        Class<?> callerClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        return runWithTimer(process, () -> new LogTimer(logMsgSupplier, getCallerLogger(callerClass)));
    }

}
