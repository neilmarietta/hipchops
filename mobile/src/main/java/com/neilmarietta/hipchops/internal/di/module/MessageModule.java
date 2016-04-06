package com.neilmarietta.hipchops.internal.di.module;

import com.neilmarietta.hipchops.data.repository.WebPageTitleRepository;
import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleLocalProvider;
import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.internal.di.PerMessage;
import com.neilmarietta.hipchops.util.MessageParser;

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
    MessageParser provideMessageParser(WebPageTitleRepository webPageTitleRepository) {
        return new MessageParser(webPageTitleRepository);
    }

    @Provides
    @PerMessage
    WebPageTitleLocalProvider provideWebPageTitleLocalProvider() {
        return new WebPageTitleLocalProvider();
    }
}
