package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.data.repository.WebPageTitleRepository;
import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleLocalProvider;
import com.neilmarietta.hipchops.interactor.GetInputMessageListUseCase;
import com.neilmarietta.hipchops.util.MessageParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageListModule {

    @Provides
    @Singleton
    GetInputMessageListUseCase provideGetInputMessageListCase(MessageParser messageParser) {
        return new GetInputMessageListUseCase(messageParser);
    }

    @Provides
    @Singleton
    MessageParser provideMessageParser(WebPageTitleRepository webPageTitleRepository) {
        return new MessageParser(webPageTitleRepository);
    }

    @Provides
    @Singleton
    WebPageTitleLocalProvider provideWebPageTitleLocalProvider() {
        return new WebPageTitleLocalProvider();
    }
}
