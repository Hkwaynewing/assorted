package assorted.atl;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class RateLimiterTest {

    @Test
    void rateLimit() {
        RateLimiter rateLimiter = new RateLimiter();
        assertEquals(true, rateLimiter.rateLimit(1));
        assertEquals(true, rateLimiter.rateLimit(1));
        assertEquals(true, rateLimiter.rateLimit(1));
        assertEquals(false, rateLimiter.rateLimit(1));
    }
}