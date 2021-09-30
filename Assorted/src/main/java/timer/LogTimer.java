package timer;

import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class LogTimer extends BaseTimer {

    private static final Logger logger = LoggerFactory.getLogger(LogTimer.class);
    private static final Level DEFAULT_LOG_LEVEL = Level.INFO;
    private final Supplier<String> logMsgSupplier;
    private Logger callerLogger;

    public enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }

    public LogTimer(Supplier<String> logMsgSupplier) {
        this.logMsgSupplier = logMsgSupplier;
    }

    public LogTimer(Supplier<String> logMsgSupplier, Logger logger) {
        this.logMsgSupplier = logMsgSupplier;
        callerLogger = logger;
    }

    @Override
    protected void postProcess() {
        log(getLogger(), getLogLevel(), logMsgSupplier.get(), duration);
    }

    protected Level logLevelToUse() {
        return null;
    }

    private Logger getLogger() {
        return callerLogger == null ? logger : callerLogger;
    }

    private Level getLogLevel() {
        return logLevelToUse() == null ? DEFAULT_LOG_LEVEL : logLevelToUse();
    }

    /**
     * Log at the specified level
     */
    private void log(Logger logger, Level level, String msg, Object... args) {
        Object[] argsArray = Arrays.stream(args).toArray();
        switch (level) {
            case TRACE:
                logger.trace(msg, argsArray);
                break;
            case DEBUG:
                logger.debug(msg, argsArray);
                break;
            case INFO:
                logger.info(msg, argsArray);
                break;
            case WARN:
                logger.warn(msg, argsArray);
                break;
            case ERROR:
                logger.error(msg, argsArray);
                break;
        }
    }

}
