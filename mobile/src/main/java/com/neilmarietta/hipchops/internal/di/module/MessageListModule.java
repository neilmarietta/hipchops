package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.interactor.GetInputMessageListUseCase;
import com.neilmarietta.hipchops.interactor.UseCase;
import com.neilmarietta.hipchops.util.MessageParser;
import com.neilmarietta.hipchops.util.WebPageTitleInternetProvider;
import com.neilmarietta.hipchops.util.WebPageTitleProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageListModule {

    @Provides
    @Singleton
    UseCase provideGetInputMessageListCase(MessageParser messageParser) {
        return new GetInputMessageListUseCase(messageParser);
    }

    @Provides
    @Singleton
    MessageParser provideMessageParser(WebPageTitleProvider webPageTitleProvider) {
        return new MessageParser(webPageTitleProvider);
    }

    @Provides
    @Singleton
    WebPageTitleProvider provideWebPageTitleProvider() {
        return new WebPageTitleInternetProvider();
    }
}
