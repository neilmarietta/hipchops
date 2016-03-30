package com.neilmarietta.hipchops.data.repository;

import com.neilmarietta.hipchops.data.repository.datasource.EmoticonDataStore;
import com.neilmarietta.hipchops.data.repository.datasource.EmoticonDataStoreFactory;
import com.neilmarietta.hipchops.entity.Emoticon;

import java.util.List;

import rx.Observable;

public class EmoticonRepository implements EmoticonDataStore {

    private final EmoticonDataStoreFactory mEmoticonDataStoreFactory;

    public EmoticonRepository(EmoticonDataStoreFactory emoticonDataStoreFactory) {
        mEmoticonDataStoreFactory = emoticonDataStoreFactory;
    }

    @Override
    public Observable<Emoticon> getEmoticon(String emoticon_id_or_shortcut) {
        return mEmoticonDataStoreFactory.create(emoticon_id_or_shortcut).getEmoticon(emoticon_id_or_shortcut);
    }

    @Override
    public Observable<List<Emoticon>> getEmoticons() {
        // Get all Emoticons from the Cloud
        return mEmoticonDataStoreFactory.createCloudEmoticonDataStore().getEmoticons();
    }
}
