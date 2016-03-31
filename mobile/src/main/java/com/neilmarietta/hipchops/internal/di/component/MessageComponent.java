package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.internal.di.module.MessageModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MessageModule.class})
public interface MessageComponent {
    ParseMessageUseCase parseMessageUseCase();
}
