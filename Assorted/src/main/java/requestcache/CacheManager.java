package requestCache;

/**
 * Simple in memory cache with no invalidations. An instance should be short-lived, preferably just a single instance
 * per transaction to avoid stale entries.
 */
public class CacheManager {
    private final Map<Long, Optional<String>> longKeyCache = new WeakHashMap<>();
    private final Map<String, Optional<Integer>> stringKeyCache = new WeakHashMap<>();

    public <K, V> V getCache(K key, Function<K, V> cacheLoader) {
        Map<K, V> mapToUse = getMap(key);

        return Optional.ofNullable(mapToUse.get(key))
                // cache miss
                .orElseGet(() -> {
                    V value = cacheLoader.apply(key);
                    mapToUse.put(key, value);
                    return value;
                });
    }

    private <K> Map getMap(K key) {
        if (key instanceof Long) {
            return longKeyCache;
        } else {
            return stringKeyCache;
        }
    }
}