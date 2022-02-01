package leetcode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import traffic.TrafficProcessor;

public class TrafficProcessorTest {

    private String filepath = "src/test/resources/traffic";


    private TrafficProcessor processor;

    @Before
    public void setUp() {
        processor = new TrafficProcessor(filepath);
    }

    @Test
    public void getMax() {
        Assert.assertEquals(46, processor.getMax());
    }

    @Test
    public void getDailySum() {
        Assert.assertEquals(10, (int) processor.getDailySum().get("2016-12-09"));
    }

    @Test
    public void getTopHours() {
        Assert.assertEquals("2016-12-01T07:30:00 46", processor.getTopHalfHours(1));
        Assert.assertEquals("2016-12-01T07:30:00 46\n"
                + "2016-12-01T08:00:00 42\n"
                + "2016-12-05T11:30:00 42", processor.getTopHalfHours(3));
    }
}