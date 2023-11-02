package xyz.mpdn.ej.guavacache;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class GuavaCacheServiceTest {
    private final Logger logger = LoggerFactory.getLogger(GuavaCacheServiceTest.class);
    private GuavaCacheService cacheService;

    @Before
    public void setUp() {
        cacheService = new GuavaCacheService(2);
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
    public void testCacheStatistics() throws InterruptedException {
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value2");

        // Check total cache evictions
        assertEquals(0, cacheService.getStatistics().evictionCount());
        cacheService.put("key3", "value3");
        logger.debug("Eviction count: {}", cacheService.getStatistics().evictionCount());

        assertEquals(1, cacheService.getStatistics().evictionCount());
        assertTrue(cacheService.getStatistics().totalLoadTime() >= 0);
        assertTrue(cacheService.getStatistics().totalLoadTime() >= 0);
    }
}