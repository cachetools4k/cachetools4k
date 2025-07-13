package io.github.cachetools4k

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LruCacheTest {

    @Test
    fun `test eviction of eldest entry when max size exceeded`() {
        val cache = LruCache<String, String>(2)
        cache.put("key1", "value1")
        cache.put("key2", "value2")
        cache.put("key3", "value3")

        assertFalse(cache.contains("key1"), "The eldest entry was not evicted correctly")
        assertTrue(cache.contains("key2"))
        assertTrue(cache.contains("key3"))
    }

    @Test
    fun `test adding and retrieving elements`() {
        val cache = LruCache<String, String>(2)
        cache.put("key1", "value1")
        cache.put("key2", "value2")

        assertEquals("value1", cache.get("key1"))
        assertEquals("value2", cache.get("key2"))
    }

    @Test
    fun `test removing an element`() {
        val cache = LruCache<String, String>(2)
        cache.put("key1", "value1")
        cache.put("key2", "value2")

        assertEquals("value1", cache.remove("key1"))
        assertNull(cache.get("key1"))
    }

    @Test
    fun `test clearing all elements`() {
        val cache = LruCache<String, String>(2)
        cache.put("key1", "value1")
        cache.put("key2", "value2")

        cache.clear()
        assertEquals(0, cache.size())
        assertFalse(cache.contains("key1"))
        assertFalse(cache.contains("key2"))
    }

    @Test
    fun `test cache size`() {
        val cache = LruCache<String, String>(3)
        cache.put("key1", "value1")
        cache.put("key2", "value2")
        cache.put("key3", "value3")

        assertEquals(3, cache.size())
    }

    @Test
    fun `test key containment`() {
        val cache = LruCache<String, String>(2)
        cache.put("key1", "value1")
        cache.put("key2", "value2")

        assertTrue(cache.contains("key1"))
        assertTrue(cache.contains("key2"))
        assertFalse(cache.contains("key3"))
    }

    @Test
    fun `test accessing element updates its recency`() {
        val cache = LruCache<String, String>(2)
        cache.put("key1", "value1")
        cache.put("key2", "value2")
        cache.get("key1")
        cache.put("key3", "value3")

        assertTrue(cache.contains("key1"), "Recently accessed key1 should not be evicted")
        assertFalse(cache.contains("key2"), "Least recently used key2 should be evicted")
        assertTrue(cache.contains("key3"))
    }
}