package assorted;

import static org.junit.Assert.assertEquals;

import static assorted.IngestionTopics.getVal1;

import org.junit.jupiter.api.Test;

class AssortedTest {

    @Test
    public void testGetTopic() {
//        assertEquals("hey", getTopic(IngestionTopics.Stage.INGESTED, null));
        assertEquals(1,
                getVal1(IngestionTopics.Enum.VALUE_ONE));
    }
}