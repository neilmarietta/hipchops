package com.neilmarietta.hipchops.interactor;

import com.neilmarietta.hipchops.data.repository.EmoticonRepository;

import rx.Observable;

public class GetEmoticonUseCase extends UseCase {

    private String mEmoticonIdOrShortcut;
    private EmoticonRepository mEmoticonRepository;

    public GetEmoticonUseCase(String emoticon_id_or_shortcut, EmoticonRepository emoticonRepository) {
        mEmoticonIdOrShortcut = emoticon_id_or_shortcut;
        mEmoticonRepository = emoticonRepository;
    }

    public Observable buildObservable() {
        return mEmoticonRepository.getEmoticon(mEmoticonIdOrShortcut);
    }
}
