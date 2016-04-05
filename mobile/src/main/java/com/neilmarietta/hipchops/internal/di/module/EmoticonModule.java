package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.interactor.GetEmoticonUseCase;
import com.neilmarietta.hipchops.internal.di.PerUser;

import dagger.Module;
import dagger.Provides;

@Module
public class EmoticonModule {

    private final String mEmoticonIdOrShortcut;

    public EmoticonModule(String emoticon_id_or_shortcut) {
        mEmoticonIdOrShortcut = emoticon_id_or_shortcut;
    }

    @Provides
    @PerUser
    GetEmoticonUseCase provideGetEmoticonUseCase(EmoticonRepository emoticonRepository) {
        return new GetEmoticonUseCase(mEmoticonIdOrShortcut, emoticonRepository);
    }
}
