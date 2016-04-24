package com.neilmarietta.hipchops.data.repository.datasource;

import com.neilmarietta.hipchops.data.cache.Cache;
import com.neilmarietta.hipchops.data.cache.EmoticonCache;
import com.neilmarietta.hipchops.entity.Emoticon;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class CachedEmoticonDataStore implements EmoticonDataStore {

    private final EmoticonCache mEmoticonCache;

    public CachedEmoticonDataStore(EmoticonCache cache) {
        mEmoticonCache = cache;
    }

    @Override
    public Observable<Emoticon> getEmoticon(final String emoticon_id_or_shortcut) {
        return Observable.create(new Observable.OnSubscribe<Emoticon>() {
            @Override
            public void call(Subscriber<? super Emoticon> subscriber) {
                Cache.Entry<Emoticon> entry = mEmoticonCache.get(emoticon_id_or_shortcut);
                if (entry != null) {
                    subscriber.onNext(entry.getData());
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public Observable<List<Emoticon>> getEmoticons() {
        throw new UnsupportedOperationException("Cannot cache List<Emoticon>");
    }
}
