package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.internal.di.PerMessage;
import com.neilmarietta.hipchops.internal.di.module.MessageModule;

import dagger.Component;

@PerMessage
@Component(modules = {MessageModule.class})
public interface MessageComponent {
    ParseMessageUseCase parseMessageUseCase();
}
