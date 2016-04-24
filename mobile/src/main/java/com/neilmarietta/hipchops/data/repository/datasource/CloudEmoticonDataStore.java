package com.neilmarietta.hipchops.data.repository.datasource;

import com.neilmarietta.hipchops.data.cache.Cache;
import com.neilmarietta.hipchops.data.cache.EmoticonCache;
import com.neilmarietta.hipchops.data.repository.net.EmoticonRestApi;
import com.neilmarietta.hipchops.entity.Emoticon;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Action1;

public class CloudEmoticonDataStore implements EmoticonDataStore {

    private final EmoticonRestApi mRestService;
    private final EmoticonCache mEmoticonCache;

    private final Action1<Emoticon> mSaveToCacheAction = new Action1<Emoticon>() {
        @Override
        public void call(Emoticon emoticon) {
            if (emoticon != null) {
                // Get by shortcut
                mEmoticonCache.put(emoticon.getShortcut(), new Cache.Entry<>(emoticon));
                // Get by id
                mEmoticonCache.put(Integer.toString(emoticon.getId()), new Cache.Entry<>(emoticon));
            }
        }
    };

    public CloudEmoticonDataStore(Retrofit adapter, EmoticonCache cache) {
        mRestService = adapter.create(EmoticonRestApi.class);
        mEmoticonCache = cache;
    }

    @Override
    public Observable<Emoticon> getEmoticon(String emoticon_id_or_shortcut) {
        return mRestService.getEmoticon(emoticon_id_or_shortcut).doOnNext(mSaveToCacheAction);
    }

    @Override
    public Observable<List<Emoticon>> getEmoticons() {
        return mRestService.getEmoticons();
    }
}
