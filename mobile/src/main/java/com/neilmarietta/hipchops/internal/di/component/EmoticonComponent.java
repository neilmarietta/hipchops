package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.interactor.GetEmoticonUseCase;
import com.neilmarietta.hipchops.internal.di.module.ApiConnectionModule;
import com.neilmarietta.hipchops.internal.di.module.EmoticonModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiConnectionModule.class, EmoticonModule.class})
public interface EmoticonComponent {
    GetEmoticonUseCase emoticonUseCase();
}
