package com.neilmarietta.hipchops.data.cache;

import android.support.v4.util.LruCache;

public abstract class GenericLruCache<T> implements Cache<T> {

    private LruCache<String, Entry<T>> sLruCache;

    public GenericLruCache(int maxEntries) {
        if (sLruCache == null)
            sLruCache = new LruCache<>(maxEntries);
    }

    @Override
    public boolean inCache(String key) {
        return sLruCache.get(key) != null;
    }

    @Override
    public Entry<T> get(String key) {
        return sLruCache.get(key);
    }

    @Override
    public void put(String key, Entry<T> entry) {
        sLruCache.put(key, entry);
    }

    @Override
    public void remove(String key) {
        sLruCache.remove(key);
    }

    @Override
    public void clear() {
        sLruCache.evictAll();
    }
}
