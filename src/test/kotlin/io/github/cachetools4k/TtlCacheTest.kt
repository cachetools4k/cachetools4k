package io.github.cachetools4k

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TtlCacheTest {

    @Test
    fun testPutAndGet() {
        val cache = TtlCache<String, String>(5000)
        cache.put("key", "value")
        assertEquals("value", cache.get("key"))
    }

    @Test
    fun testExpiredEntriesAreRemoved() {
        val cache = TtlCache<String, String>(100)
        cache.put("key", "value")
        Thread.sleep(500)
        assertNull(cache.get("key"))
    }

    @Test
    fun testRemove() {
        val cache = TtlCache<String, String>(1000)
        cache.put("key", "value")
        cache.remove("key")
        assertNull(cache.get("key"))
    }

    @Test
    fun testClear() {
        val cache = TtlCache<String, String>(1000)
        cache.put("key1", "value1")
        cache.put("key2", "value2")
        cache.clear()
        assertEquals(0, cache.size())
    }

    @Test
    fun testSize() {
        val cache = TtlCache<String, String>(100)
        cache.put("key1", "value1")
        Thread.sleep(200)
        cache.put("key2", "value2")
        assertEquals(1, cache.size())
    }

    @Test
    fun testContains() {
        val cache = TtlCache<String, String>(100)
        cache.put("key", "value")
        assertTrue(cache.contains("key"))
        Thread.sleep(200)
        assertFalse(cache.contains("key"))
    }

    @Test
    fun testInitializationThrowsExceptionForInvalidTtl() {
        assertThrows(IllegalArgumentException::class.java) {
            TtlCache<String, String>(0)
        }
    }
}