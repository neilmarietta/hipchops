package com.neilmarietta.hipchops.data.repository.net;

import com.neilmarietta.hipchops.entity.Emoticon;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface EmoticonRestApi {

    @GET("/v2/emoticon/{id}")
    Observable<Emoticon> getEmoticon(@Path("id") String emoticon_id_or_shortcut);

    @GET("/v2/emoticon")
    Observable<List<Emoticon>> getEmoticons();
}
