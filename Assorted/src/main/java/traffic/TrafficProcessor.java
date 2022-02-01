package traffic;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

public class TrafficProcessor {

    private static final int DATA_SIZE = 32;

    private int maxCount = Integer.MIN_VALUE;

    /**
     * date to date count sum map - (2016-12-01, 101)
     */
    private final Map<String, Integer> dateStrToCountMap = new HashMap<>(DATA_SIZE);

    /**
     * count to list of date String map - (42, {2016-12-01T08:00:00, 2016-12-05T11:30:00})
     */
    private final Map<Integer, List<String>> countToTimesMap = new TreeMap<>(Collections.reverseOrder());


    /**
     * arrays contains accumulative sum of counts
     */
    private final int[] accumulativeSum = new int[DATA_SIZE + 1];

    public TrafficProcessor(String filepath) {
        init(filepath);
    }

    public int getMax() {
        return maxCount;
    }

    public Map<String, Integer> getDailySum() {
        return dateStrToCountMap;
    }

    /**
     * if >1 values for a count, sort by time asc
     */
    public String getTopHalfHours(int limit) {
        //validate limit

        StringBuilder resultBuilder = new StringBuilder();

        for (Entry<Integer, List<String>> entry : countToTimesMap.entrySet()) {
            for (String time : entry.getValue()) {
                resultBuilder.append(time).append(" ").append(entry.getKey());
                if (--limit == 0) {
                    return resultBuilder.toString();
                }
                resultBuilder.append(System.lineSeparator());
            }
        }
        return "";
    }

    public int getConsecutiveMin(int size) {
        // build accumulative sum array in init()
        /**
         *      2	10	5	3	5	2	4
         *
         * 0	2	12	17	20	25	27	31
         *              0	2	12	17	20	25	27	31
         *              ------------------
         *              17	18	13	10	11
         */

        return 0;
    }

    private void init(String filepath) {
        List<String> lines = readFile(filepath);

        for (String line : lines) {
            // 2016-12-05T11:30:00 42
            String[] split = line.split(" ");
            int count = Integer.parseInt(split[1]);

            calculateMax(count);

            calculateDailySum(split[0], count);

            buildCountToTimeMap(split[0], count);

            buildAccumulativeSumArray(count);
        }
    }

    private void buildCountToTimeMap(String e, int count) {
        List<String> timeList = countToTimesMap.getOrDefault(count, new ArrayList<>());
        timeList.add(e);
        countToTimesMap.put(count, timeList);
    }

    /**
     * since data is sorted by date asc, can actually add stats up using local variable vs getting it from map
     */
    private void calculateDailySum(String s, int count) {
        String dateString = s.split("T")[0];
        dateStrToCountMap.put(dateString, dateStrToCountMap.getOrDefault(dateString, 0) + count);
    }

    private void calculateMax(int count) {
        maxCount = Math.max(maxCount, count);
    }

    private void buildAccumulativeSumArray(int count) {
        //TODO
    }

    private List<String> readFile(String filepath) {
        List<String> lines;
        try {
            lines = Files.readLines(new File(filepath), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("Unable to open file at " + filepath);
        }
        return lines;
    }
}

