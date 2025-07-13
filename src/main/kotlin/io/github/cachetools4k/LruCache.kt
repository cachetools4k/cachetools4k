package io.github.cachetools4k

/**
 * A Least Recently Used (LRU) cache implementation that automatically removes the least recently accessed entries
 * when the cache size exceeds the specified maximum capacity.
 *
 * This implementation uses [LinkedHashMap] with access-order iteration to maintain the LRU order of entries.
 *
 * @param K The type of keys used to identify values in the cache
 * @param V The type of values stored in the cache
 * @property maxSize The maximum number of entries the cache can hold before removing least recently used entries
 */
class LruCache<K, V>(private val maxSize: Int): Cache<K, V> {
    
    private val map = object : LinkedHashMap<K, V>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
            return size > maxSize
        }
    }

    /**
     * Retrieves a value associated with the given key from the cache.
     * Accessing an entry makes it the most recently used one.
     *
     * @param key The key whose associated value is to be returned
     * @return The value associated with the key, or null if the key is not present in the cache
     */
    override fun get(key: K): V? = map[key]

    /**
     * Associates the specified value with the specified key in the cache.
     * If the cache reaches its size limit, the least recently used entry will be removed.
     *
     * @param key The key with which the specified value is to be associated
     * @param value The value to be associated with the specified key
     */
    override fun put(key: K, value: V) {
        map[key] = value
    }

    /**
     * Removes the entry for the specified key from the cache.
     *
     * @param key The key whose mapping is to be removed from the cache
     * @return The previous value associated with the key, or null if there was no mapping
     */
    override fun remove(key: K): V? = map.remove(key)

    /**
     * Removes all entries from the cache, making it empty.
     */
    override fun clear() = map.clear()

    /**
     * Returns the number of key-value mappings in the cache.
     *
     * @return The number of entries in the cache
     */
    override fun size(): Int = map.size

    /**
     * Checks if the cache contains a mapping for the specified key.
     *
     * @param key The key whose presence in the cache is to be tested
     * @return true if the cache contains a mapping for the specified key, false otherwise
     */
    override fun contains(key: K): Boolean = map.containsKey(key)

    override fun toString(): String = map.toString()
}