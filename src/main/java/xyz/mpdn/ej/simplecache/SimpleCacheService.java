package xyz.mpdn.ej.simplecache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class SimpleCacheService {
    private static final int MAX_SIZE = 100000;
    private static final long EVICTION_TIME = 5000; // 5 seconds
    private final Cache cache;
    private final ReentrantLock lock = new ReentrantLock();
    private final Logger logger = LoggerFactory.getLogger(SimpleCacheService.class);
    private final Statistics statistics = new Statistics();

    public SimpleCacheService() {
        this(MAX_SIZE);
    }

    public SimpleCacheService(int cacheSize) {
        this.cache = new Cache(
                /* max_size= */ Math.min(cacheSize, MAX_SIZE),
                /* onEldestRemove= */ eldest -> {
            logger.info("Removed entry: Key = {}, Value = {}", eldest.getKey(), eldest.getValue());
            statistics.incrementCacheEvictions();
        });
    }

    public String get(String key) {
        lock.lock();
        try {
            CacheEntry entry = cache.get(key);
            if (entry != null && (System.currentTimeMillis() - entry.getLastAccessTime() <= EVICTION_TIME)) {
                return entry.getData();
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void put(String key, String value) {
        lock.lock();
        try {
            CacheEntry cacheEntry = new CacheEntry(value);
            cache.put(key, cacheEntry);
            logger.info("Put into cache: Key = {}", key);
            statistics.addPutTime(System.currentTimeMillis() - cacheEntry.getLastAccessTime());
        } finally {
            lock.unlock();
        }
    }

    public Statistics getStatistics() {
        return statistics;
    }

    private int getMaxSize() {
        return cache.getMaxSize();
    }


    private final static class Cache extends LinkedHashMap<String, CacheEntry> {
        private final int max_size;
        private final Consumer<Map.Entry<String, CacheEntry>> onEldestRemove;

        private Cache(int maxSize, Consumer<Map.Entry<String, CacheEntry>> onEldestRemove) {
            super(maxSize, 0.75f, true);
            max_size = maxSize;
            this.onEldestRemove = onEldestRemove;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, CacheEntry> eldest) {
            if (size() > max_size) {
                onEldestRemove.accept(eldest);
                return true;
            }
            return false;
        }

        public int getMaxSize() {
            return max_size;
        }
    }
}
