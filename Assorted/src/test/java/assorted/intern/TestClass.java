package assorted.intern;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestClass {

    @Test
    void testWithoutIntern() {
        String st1 = new String("st1");
        String st2 = new String("st2");
        String st3 = new String("st1 ");
        final List<String> strings = Arrays.asList(st1, st2, st3);
        List<String> arr = new ArrayList<>();
        for (String vall : strings) {
            final String cleaned = new String(vall).trim();
            arr.add(cleaned);
        }

        assertNotSame(arr.get(0), arr.get(2));
    }

    @Test
    void testIntern() {
        String st1 = new String("st1");
        String st2 = new String("st2");
        String st3 = new String("st1 ");
        final List<String> strings = Arrays.asList(st1, st2, st3);
        List<String> arr = new ArrayList<>();
        for (String vall : strings) {
            final String cleaned = new String(vall).trim().intern();
            arr.add(cleaned);
        }

        assertSame(arr.get(0), arr.get(2));
        // 2 obj
        assertNotSame(arr.get(0), st1);
    }

    @Test
    void testIntern_reuseObj() {
        String st1 = new String("st1");
        String st2 = new String("st2");
        String st3 = new String("st1 ");
        final List<String> strings = Arrays.asList(st1, st2, st3);
        List<String> arr = new ArrayList<>();
        for (String vall : strings) {
            final String cleaned = new String(vall).trim().intern();
            if (vall.equals(cleaned))
                arr.add(vall);
            else
                arr.add(cleaned);
        }

        //only 1 obj if same
        assertSame(arr.get(0), st1);
        assertNotSame(arr.get(2), st3);
    }
}
