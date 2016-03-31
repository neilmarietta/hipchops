package com.neilmarietta.hipchops.data.repository.datasource;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class EmoticonDataStoreFactory {

    private final Retrofit mAdapter;

    @Inject
    public EmoticonDataStoreFactory(Retrofit adapter) {
        mAdapter = adapter;
    }

    public EmoticonDataStore create(String emoticon_id_or_shortcut) {
        return /*isOffline() || inCache(emoticon_id_or_shortcut) ?
                createDiskEmoticonDataStore() : */
                createCloudEmoticonDataStore();
    }

    public EmoticonDataStore createCloudEmoticonDataStore() {
        return new CloudEmoticonDataStore(mAdapter);
    }
}
