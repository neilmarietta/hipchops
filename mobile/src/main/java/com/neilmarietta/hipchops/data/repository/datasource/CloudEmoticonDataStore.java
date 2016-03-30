package com.neilmarietta.hipchops.data.repository.datasource;

import com.neilmarietta.hipchops.data.repository.net.EmoticonRestApi;
import com.neilmarietta.hipchops.entity.Emoticon;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

public class CloudEmoticonDataStore implements EmoticonDataStore {

    private EmoticonRestApi mRestService;

    public CloudEmoticonDataStore(Retrofit adapter) {
        mRestService = adapter.create(EmoticonRestApi.class);
    }

    @Override
    public Observable<Emoticon> getEmoticon(String emoticon_id_or_shortcut) {
        return mRestService.getEmoticon(emoticon_id_or_shortcut);
    }

    @Override
    public Observable<List<Emoticon>> getEmoticons() {
        return mRestService.getEmoticons();
    }
}
