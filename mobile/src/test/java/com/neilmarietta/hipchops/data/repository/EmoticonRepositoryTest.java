package com.neilmarietta.hipchops.data.repository;

import com.neilmarietta.hipchops.entity.Emoticon;
import com.neilmarietta.hipchops.internal.di.component.DaggerRepositoriesComponent;

import org.junit.Test;

import rx.Subscriber;

import static org.junit.Assert.assertEquals;

public class EmoticonRepositoryTest {

    private static EmoticonRepository mEmoticonRepository =
            DaggerRepositoriesComponent.create().emoticonRepository();

    @Test
    public void getEmoticonTest() {
        mEmoticonRepository.getEmoticon("fonzie")
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
