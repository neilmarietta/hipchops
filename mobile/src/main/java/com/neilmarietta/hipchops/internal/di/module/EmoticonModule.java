package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.interactor.GetEmoticonUseCase;
import com.neilmarietta.hipchops.interactor.UseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class EmoticonModule {

    private final String mEmoticonIdOrShortcut;

    public EmoticonModule(String emoticon_id_or_shortcut) {
        mEmoticonIdOrShortcut = emoticon_id_or_shortcut;
    }

    @Provides
    String provideEmoticonIdOrShortcut() {
        return mEmoticonIdOrShortcut;
    }

    @Provides
    UseCase provideGetEmoticonUseCase(EmoticonRepository emoticonRepository) {
        return new GetEmoticonUseCase(mEmoticonIdOrShortcut, emoticonRepository);
    }
}
