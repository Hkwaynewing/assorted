package requestcache;

class CacheManagerTest{

    @Test
    public void testRequestType() {

        CacheManager bookingCache = new CacheManager();

        assertEquals("1value", bookingCache.getFromCache(1L, getLongStringFunction()));
        assertEquals("1value", bookingCache.getFromCache(1L, getLongStringFunction()));
        bookingCache.getFromCache("22", Integer::parseInt);
        bookingCache.getFromCache("22", Integer::parseInt);
        assertNull(bookingCache.getFromCache("33", (key) -> null));
        assertNull(bookingCache.getFromCache("33", (key) -> null));
    }


}