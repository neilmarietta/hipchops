package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.interactor.GetEmoticonUseCase;
import com.neilmarietta.hipchops.internal.di.PerUser;
import com.neilmarietta.hipchops.internal.di.module.EmoticonModule;

import dagger.Component;

@PerUser
@Component(dependencies = {ApiConnectionComponent.class}, modules = {EmoticonModule.class})
public interface EmoticonComponent {
    GetEmoticonUseCase emoticonUseCase();
}
