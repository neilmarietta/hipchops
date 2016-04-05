package com.neilmarietta.hipchops.data.cache;

public interface Cache<T extends Object> {

    /**
     * Return true if the corresponding key entry is in cache.
     *
     * @param key Cache key
     */
    boolean inCache(String key);

    /**
     * Retrieves an entry from the cache.
     *
     * @param key Cache key
     * @return An {@link Entry} or null in the event of a cache miss
     */
    Entry<T> get(String key);

    /**
     * Adds or replaces an entry to the cache.
     *
     * @param key   Cache key
     * @param entry Data to store and metadata, TTL, etc.
     */
    void put(String key, Entry<T> entry);

    /**
     * Removes an entry from the cache.
     *
     * @param key Cache key
     */
    void remove(String key);

    /**
     * Empties the cache.
     */
    void clear();

    /**
     * Data and metadata for an entry returned by the cache.
     */
    class Entry<T extends Object> {
        private T mData;
        private long lastTimeMillis;
        private long ttl;

        public Entry(T data) {
            mData = data;
        }

        public T getData() {
            return mData;
        }
    }
}
