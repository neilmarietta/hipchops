package com.neilmarietta.hipchops.data.repository.datasource;

import com.neilmarietta.hipchops.data.cache.EmoticonCache;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class EmoticonDataStoreFactory {

    private final EmoticonCache mEmoticonCache;
    private final Retrofit mAdapter;

    @Inject
    public EmoticonDataStoreFactory(EmoticonCache cache, Retrofit adapter) {
        mEmoticonCache = cache;
        mAdapter = adapter;
    }

    public EmoticonDataStore create(String emoticon_id_or_shortcut) {
        return mEmoticonCache.inCache(emoticon_id_or_shortcut) ?
                createCachedEmoticonDataStore() :
                createCloudEmoticonDataStore();
    }

    public EmoticonDataStore createCachedEmoticonDataStore() {
        return new CachedEmoticonDataStore(mEmoticonCache);
    }

    public EmoticonDataStore createCloudEmoticonDataStore() {
        return new CloudEmoticonDataStore(mAdapter, mEmoticonCache);
    }
}
