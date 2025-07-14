package io.github.cachetools4k

/**
 * A cache implementation that automatically expires entries after a specified time-to-live (TTL) duration.
 *
 * @param K the type of keys maintained by this cache
 * @param V the type of mapped values
 * @param ttlMillis the time-to-live duration in milliseconds for cache entries
 */
class TtlCache<K, V>(private val ttlMillis: Long) : Cache<K, V> {

    init {
        require(ttlMillis > 0) { "ttlMillis must be greater than 0" }
    }

    private data class Entry<V>(val value: V, val timestamp: Long)

    private val map = mutableMapOf<K, Entry<V>>()

    /**
     * Returns the value associated with the given [key], or null if the key is not present
     * or the entry has expired.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null if not found or expired
     */
    override fun get(key: K): V? {
        val entry = map[key]
        if (entry != null) {
            if (isExpired(entry)) {
                map.remove(key)
                return null
            }
            return entry.value
        }
        return null
    }

    /**
     * Associates the specified [value] with the specified [key] in this cache.
     * If the cache previously contained a mapping for the key, the old value is replaced.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    override fun put(key: K, value: V) {
        map[key] = Entry(value, System.currentTimeMillis())
    }

    /**
     * Removes the entry for the specified [key] if present.
     *
     * @param key the key whose mapping is to be removed from the cache
     * @return the previous value associated with the key, or null if there was no mapping
     */
    override fun remove(key: K): V? {
        return map.remove(key)?.value
    }

    /**
     * Removes all entries from this cache.
     */
    override fun clear() {
        map.clear()
    }

    /**
     * Returns the number of entries in this cache after removing any expired entries.
     *
     * @return the number of active entries in the cache
     */
    override fun size(): Int {
        evictExpiredEntries()
        return map.size
    }

    /**
     * Returns true if this cache contains a non-expired mapping for the specified [key].
     *
     * @param key the key whose presence in this cache is to be tested
     * @return true if this cache contains a non-expired mapping for the specified key
     */
    override fun contains(key: K): Boolean {
        val entry = map[key]
        if (entry != null && isExpired(entry)) {
            map.remove(key)
            return false
        }
        return entry != null
    }
    
    private fun evictExpiredEntries() {
        val now = System.currentTimeMillis()
        val iterator = map.entries.iterator()
        while (iterator.hasNext()) {
            val (_, entry) = iterator.next()
            if (now - entry.timestamp > ttlMillis) {
                iterator.remove()
            }
        }
    }

    private fun isExpired(entry: Entry<V>): Boolean = System.currentTimeMillis() - entry.timestamp > ttlMillis
}