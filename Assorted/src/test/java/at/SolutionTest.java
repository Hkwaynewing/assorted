package at;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SolutionTest {

    @Test
    void findWinner() {
        FindWinner solution = new FindWinner();
        Vote v1 = new Vote(Arrays.asList("u1", "u2", "u3"));
        Vote v2 = new Vote(Arrays.asList("u1", "u3", "u2"));
        Vote v3 = new Vote(Arrays.asList("u1", "u3"));
        List<String> winner = solution.findWinner(Arrays.asList(v1, v2, v3));
        assertEquals("u1", winner.get(0));
        assertEquals("u3", winner.get(1));
        assertEquals("u2", winner.get(2));
    }

    @Test
    void findWinner_moreThan3Votes() {
        FindWinner solution = new FindWinner();
        Vote v1 = new Vote(Arrays.asList("u1", "u2", "u3", "u4"));
        Vote v2 = new Vote(Arrays.asList("u1", "u3", "u2", "u5"));
        Vote v3 = new Vote(Arrays.asList("u1", "u3"));
        List<String> winner = solution.findWinner(Arrays.asList(v1, v2, v3));
        assertEquals("u1", winner.get(0));
        assertEquals("u3", winner.get(1));
        assertEquals("u2", winner.get(2));
        assertFalse(winner.contains("u4"));
        assertFalse(winner.contains("u5"));
    }
}