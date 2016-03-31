package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.interactor.UseCase;
import com.neilmarietta.hipchops.util.MessageParser;
import com.neilmarietta.hipchops.util.WebPageTitleInternetProvider;
import com.neilmarietta.hipchops.util.WebPageTitleProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {

    private final String mMessage;

    public MessageModule(String message) {
        mMessage = message;
    }

    @Provides
    String provideMessage() {
        return mMessage;
    }

    @Provides
    UseCase provideParseMessageUseCase(MessageParser messageParser) {
        return new ParseMessageUseCase(mMessage, messageParser);
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
