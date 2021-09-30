package timer.runner;

import timer.LogAndNewRelicTimer;

import java.util.function.Supplier;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;
import static timer.runner.TimerUtil.getCallerLogger;

/**
 * This util is provides functions to TIME, LOG, and log in New Relic a process
 */
public class NewRelicAndLogTimerUtil {

    private NewRelicAndLogTimerUtil() {
    }

    /**
     * time a {@code process} that has no return value
     *
     * @param process               the process to time
     * @param logMsgSupplier        message to log
     * @param newRelicParamSupplier param to log in New Relic
     */
    public static void runWithNRAndLogTimer(Runnable process, Supplier<String> logMsgSupplier,
            Supplier<String> newRelicParamSupplier) {
        Class<?> callerClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        runWithTimer(process,
                () -> new LogAndNewRelicTimer(logMsgSupplier, newRelicParamSupplier, getCallerLogger(callerClass)));
    }

    /**
     * time a {@code process} that has return value
     *
     * @param process               the process to time
     * @param logMsgSupplier        message to log
     * @param newRelicParamSupplier param to log in New Relic
     * @param <T>                   return type of the process
     * @return process's return value
     */
    public static <T> T runWithNRAndLogTimer(Supplier<T> process, Supplier<String> logMsgSupplier,
            Supplier<String> newRelicParamSupplier) {
        Class<?> callerClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        return runWithTimer(process,
                () -> new LogAndNewRelicTimer(logMsgSupplier, newRelicParamSupplier, getCallerLogger(callerClass)));
    }
}
