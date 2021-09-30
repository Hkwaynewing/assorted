package com.rezdy.googlethingstodo.service.util.timer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static com.rezdy.googlethingstodo.service.util.timer.runner.TimerUtil.runWithTimer;
import static org.junit.jupiter.api.Assertions.*;

class BaseTimerTest {

    private long duration;

    public class TestTimer extends BaseTimer {

        @Override
        protected void postProcess() {
            setDuration(this.duration);
        }
    }

    private void setDuration(long duration) {
        this.duration = duration;
    }

    @Test
    void timeMethodThatTakesSomeTime_thenDurationMoreThanThatTime() {
        runWithTimer(this::sleepForSomeTime, TestTimer::new);
        assertTrue(this.duration >= TimeUnit.SECONDS.toSeconds(1L));
    }

    @Test
    void timeMethodInMilliSec_thenDurationInMilliSec() {
        runWithTimer(this::sleepForSomeTime, TestTimer::new, TimeUnit.MILLISECONDS);
        assertTrue(duration >= TimeUnit.SECONDS.toMillis(1L));
    }

    @Test
    void timeMethodWithFalseRunCondition_thenDurationRemainsZero() {
        runWithTimer(this::sleepForSomeTime, TestTimer::new, false);
        assertEquals(0, duration);
    }

    @Test
    void timeMethodWithReturnValue_thenValueReturned() {
        Integer result = runWithTimer(this::getNumber, TestTimer::new);
        assertEquals(1, result);
    }

    @Test
    void runnableThrowsException_thenExceptionCaught() {
        assertThrows(RuntimeException.class, () -> runWithTimer(this::throwException, TestTimer::new));
    }

    private void sleepForSomeTime() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getNumber() {
        return 1;
    }

    private void throwException() {
        throw new RuntimeException("Gotcha!");
    }
}
