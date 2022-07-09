package at;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import assorted.atl.RateLimiter;

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