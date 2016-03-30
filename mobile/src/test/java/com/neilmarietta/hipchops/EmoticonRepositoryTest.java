package com.neilmarietta.hipchops;

import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.data.repository.datasource.EmoticonDataStoreFactory;
import com.neilmarietta.hipchops.data.repository.net.ApiConnection;
import com.neilmarietta.hipchops.entity.Emoticon;

import org.junit.Test;

import rx.Subscriber;

import static org.junit.Assert.assertEquals;

public class EmoticonRepositoryTest {

    private static EmoticonRepository sEmoticonRepository =
            new EmoticonRepository(new EmoticonDataStoreFactory(ApiConnection.createAdapter()));

    @Test
    public void getEmoticonTest() {
        sEmoticonRepository.getEmoticon("fonzie")
                .subscribe(new Subscriber<Emoticon>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Emoticon emoticon) {
                        assertEquals(emoticon.getShortcut(), "fonzie");
                    }
                });
    }
}
