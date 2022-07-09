package assorted.afterpay;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Consider the following credit card fraud detection algorithm: =============================================================
 * A credit card transaction comprises the following elements.
 * <p>
 * hashed credit card number timestamp - of format `year-month-dayThour:minute:second` amount - of format
 * `dollars.cents`
 * <p>
 * Transactions are to be received in a file as a comma separated string of elements, one per line, eg:
 * 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00
 * <p>
 * <p>
 * A credit card will be identified as fraudulent if the sum of amounts for a unique hashed credit card number over a
 * 24-hour sliding window period exceeds the price threshold.
 * <p>
 * Write a method which takes a price threshold argument and a collection of credit card transactions, eg: 150.00
 * transactions
 * <p>
 * The collection of credit card transactions are in chronological order.
 * <p>
 * Your method output should print out the hashed credit card numbers that have been identified as fraudulent.
 */

class FraudDetection {

    public static void main(String[] args) {
        Map<String, List<Record>> cardToTranMap = new HashMap<>();
        // input
        List<Record> recList = new ArrayList<>();
        for (Record record : recList) {
            List<Record> records = cardToTranMap
                    .computeIfAbsent(record.cardNo, (v) -> new ArrayList<>());
            records.add(record);
        }

//        cardToTranMap.values()
    }

    // 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-28T13:15:54, 10.00  total sum
    // 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:55, 10.00  totalSum = totalSum-10
    // 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:56, 10.00
    // 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:57, 40.00
    // 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:57, 40.00
    // 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-30T13:15:57, 40.00
    public boolean isFraud(BigDecimal threshold, List<Record> transactions) {

        //2014-04-29T13:15:54
        //2014-04-28T13:15:54 - 2014-04-29T13:15:54 > threshold
        Record firstTran = transactions.get(0);
        LocalDateTime windowEnd = firstTran.tranTime.plusDays(1);
        BigDecimal sum = new BigDecimal(0);

        // first transaction window
        int i = 0;
        for (; i < transactions.size(); i++) {
            if (transactions.get(i).tranTime.isAfter(windowEnd)) {
                break;
            }
            BigDecimal newTotal = sum.add(transactions.get(i).tranVal);
            if (newTotal.compareTo(threshold) > 0) {
                return true;
            }
            sum = newTotal;
        }

        // first transaction window - first transaction
        BigDecimal subtract = sum.subtract(firstTran.tranVal);
        sum = subtract;
        Record secTran = transactions.get(1);

        LocalDateTime windowEnd2 = secTran.tranTime.plusDays(1);

        // -----------24h
        // 10 3 4 5 5 6        7 7 8 7
        //            i
        //    -----------24h
        // 10 3 4 5 5 6 7 7    8 7
        //
        for (; i < transactions.size(); i++) {
            if (transactions.get(i).tranTime.isAfter(windowEnd2)) {
                break;
            }
            BigDecimal newTotal = sum.add(transactions.get(i).tranVal);
            if (newTotal.compareTo(threshold) > 0) {
                return true;
            }
            sum = newTotal;
        }

        // while i> list.size()

        return false;
    }

    class Record {

        String cardNo;
        LocalDateTime tranTime;
        BigDecimal tranVal;

        public Record(String cardNo, LocalDateTime tranTime, BigDecimal tranVal) {
            this.cardNo = cardNo;
            this.tranTime = tranTime;
            this.tranVal = tranVal;
        }
    }
}
