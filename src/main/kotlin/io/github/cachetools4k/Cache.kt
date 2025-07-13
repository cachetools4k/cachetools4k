package io.github.cachetools4k

/**
 * Represents a generic cache system capable of storing and retrieving key-value pairs.
 *
 * @param K The type of keys used to identify values in the cache.
 * @param V The type of values stored in the cache.
 */
interface Cache<K, V> {
    /**
     * Retrieves a value associated with the given key from the cache.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the key, or null if the key is not present in the cache.
     */
    fun get(key: K): V?

    /**
     * Associates the specified value with the specified key in the cache.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     */
    fun put(key: K, value: V)

    /**
     * Removes the entry for the specified key from the cache.
     *
     * @param key The key whose mapping is to be removed from the cache.
     * @return The previous value associated with the key, or null if there was no mapping.
     */
    fun remove(key: K): V?

    /**
     * Removes all entries from the cache, making it empty.
     */
    fun clear()

    /**
     * Returns the number of key-value mappings in the cache.
     *
     * @return The number of entries in the cache.
     */
    fun size(): Int

    /**
     * Checks if the cache contains a mapping for the specified key.
     *
     * @param key The key whose presence in the cache is to be tested.
     * @return true if the cache contains a mapping for the specified key, false otherwise.
     */
    fun contains(key: K): Boolean 
}