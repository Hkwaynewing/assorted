package timer;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class BaseTimer implements AutoCloseable {

    protected long duration;
    private final long startTime;
    private TimeUnit timeUnit = SECONDS;

    protected BaseTimer() {
        // start timer
        startTime = currentTimeMillis();
    }

    @Override
    public void close() {
        // stop timer
        var durationMillis = currentTimeMillis() - startTime;
        duration = getTimeUnit().convert(durationMillis, MILLISECONDS);
        // process duration value
        postProcess();
    }

    /**
     * Time unit override at object level. Applicable to one object only
     *
     * @param timeUnit time unit to use
     */
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * timeUnit override at class level. Applicable to all objects of this class
     *
     * @return timeUnit to use
     */
    protected TimeUnit timeUnitToUse() {
        return null;
    }

    /**
     * should contain logics to process the duration value. e.g. log it
     */
    protected abstract void postProcess();

    private TimeUnit getTimeUnit() {
        return timeUnitToUse() == null ? timeUnit : timeUnitToUse();
    }

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
