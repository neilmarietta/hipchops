package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.internal.di.PerMessage;
import com.neilmarietta.hipchops.util.MessageParser;
import com.neilmarietta.hipchops.util.WebPageTitleInternetProvider;
import com.neilmarietta.hipchops.util.WebPageTitleProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {

    private final String mMessage;

    public MessageModule(String message) {
        mMessage = message;
    }

    @Provides
    @PerMessage
    ParseMessageUseCase provideParseMessageUseCase(MessageParser messageParser) {
        return new ParseMessageUseCase(mMessage, messageParser);
    }

    @Provides
    @PerMessage
    MessageParser provideMessageParser(WebPageTitleProvider webPageTitleProvider) {
        return new MessageParser(webPageTitleProvider);
    }

    @Provides
    @PerMessage
    WebPageTitleProvider provideWebPageTitleProvider() {
        return new WebPageTitleInternetProvider();
    }
}
