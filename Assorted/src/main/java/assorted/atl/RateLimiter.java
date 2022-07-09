package assorted.atl;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {


    private static final int X = 3;
    private static final int Y = 10;
    private static final Duration expDur = Duration.ofSeconds(10);
    private static final Map<Integer, Integer> custIdToCountMap = new HashMap<>();
    private static final Map<Integer, Long> custIdToTimeMap = new HashMap<>();

    // Each customer can make X requests per Y seconds
    // Perform rate limiting logic for provided customer ID. Return true if the
    // request is allowed, and false if it is not.
    boolean rateLimit(int customerId) {
        Long timeLastTime = custIdToTimeMap.getOrDefault(customerId, System.currentTimeMillis());

        // map from custId(int) -> count(int)
        Integer count = custIdToCountMap.getOrDefault(customerId, 0);
        custIdToCountMap.putIfAbsent(customerId, 0);
        long timeThisTime = System.currentTimeMillis();

        long curWindow = timeThisTime / expDur.toMillis();

        long duration = (timeThisTime - timeLastTime) / Y;
        expDur.minusMillis(duration);

        if (count < X) {
            count = count + 1;
            custIdToCountMap.put(customerId, count);
            return true;
        } else {
            return false;
        }

        // reset count every Y seconds

//        currenTime = Instant.now().toEpochMilli() / expDur.toMillis();
//        currentTime > custom.lastTime;

    }
}

//    class XXX {
//
//        private long window;
//        private long reqCount;
//
//    }
//
//    reset(newWindow) {
//    }
//}
//
//    long currentWindow = Instant.now().toEpochMilli() / expDur.toMillis();
//    XXX xx = map.computeIfAbsent(customerId, (k) -> new XXX(currentWindowNum));
//
// if(currentWindow>XXX.window){
//         xx.reset(currentWindow)
//         }

