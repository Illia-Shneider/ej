package xyz.mpdn.ej.guavacache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GuavaCacheService {
    private static final int MAX_SIZE = 100000;
    private static final long EVICTION_TIME = 5000; // 5 seconds
    private final int max_size;
    private final Cache<String, String> cache;

    private final Logger logger = LoggerFactory.getLogger(GuavaCacheService.class);

    public GuavaCacheService() {
        this(MAX_SIZE);
    }

    public GuavaCacheService(int max_size) {
        this.max_size = Math.min(max_size, MAX_SIZE);
        cache = CacheBuilder
                .newBuilder()
                .maximumSize(max_size)
                .expireAfterAccess(EVICTION_TIME, TimeUnit.MILLISECONDS)
                .recordStats()
                .removalListener(eldest ->
                        logger.info("Removed entry: Key = {}, Value = {}",
                                eldest.getKey(),
                                eldest.getValue()))
                .build();
    }

    public String get(String key) {
        return cache.getIfPresent(key);
    }

    public void put(String key, String value) {
        cache.put(key, value);
        logger.info("Put into cache: Key = {}", key);
    }

    public long getCacheSize() {
        return cache.size();
    }

    public long getMaxSize() {
        return max_size;
    }

    public CacheStats getStatistics() {
        return cache.stats();
    }
}
