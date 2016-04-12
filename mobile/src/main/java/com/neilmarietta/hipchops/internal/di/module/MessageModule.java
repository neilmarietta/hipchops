package com.neilmarietta.hipchops.internal.di.module;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.data.repository.WebPageTitleRepository;
import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleLocalProvider;
import com.neilmarietta.hipchops.interactor.MessageUseCase;
import com.neilmarietta.hipchops.internal.di.PerMessage;
import com.neilmarietta.hipchops.util.MessageParser;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {

    @Provides
    @PerMessage
    MessageUseCase provideMessageUseCase(Gson gson, MessageParser messageParser, EmoticonRepository emoticonRepository) {
        return new MessageUseCase(gson, messageParser, emoticonRepository);
    }

    @Provides
    @PerMessage
    Gson provideGson() {
        return new Gson();
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
