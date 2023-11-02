package xyz.mpdn.ej.simplecache;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleCacheServiceTest {
    private SimpleCacheService cacheService;

    @Before
    public void setUp() {
        cacheService = new SimpleCacheService(2);
    }

    @Test
    public void testCachePutAndGet() {
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value2");

        assertEquals("value1", cacheService.get("key1"));
        assertEquals("value2", cacheService.get("key2"));
    }

    @Test
    public void testCacheEviction() throws InterruptedException {
        cacheService.put("key1", "value1");
        Thread.sleep(6000); // Wait for eviction
        assertNull(cacheService.get("key1"));
    }

    @Test
    public void testCacheStatistics() {
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value2");

        // Check total cache evictions
        assertEquals(0, cacheService.getStatistics().getTotalCacheEvictions());
        cacheService.put("key3", "value3");

        assertEquals(1, cacheService.getStatistics().getTotalCacheEvictions());
        assertTrue(cacheService.getStatistics().getAveragePutTime() >= 0);
        assertTrue(cacheService.getStatistics().getTotalPutTime() >= 0);
    }

}