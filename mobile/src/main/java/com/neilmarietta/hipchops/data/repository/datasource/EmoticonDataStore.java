package com.neilmarietta.hipchops.data.repository.datasource;

import com.neilmarietta.hipchops.entity.Emoticon;

import java.util.List;

import rx.Observable;

public interface EmoticonDataStore {

    Observable<Emoticon> getEmoticon(String emoticon_id_or_shortcut);

    Observable<List<Emoticon>> getEmoticons();
}
