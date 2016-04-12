package com.neilmarietta.hipchops.internal.di.module;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.data.repository.WebPageTitleRepository;
import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleLocalProvider;
import com.neilmarietta.hipchops.interactor.MessageUseCase;
import com.neilmarietta.hipchops.internal.di.PerActivity;
import com.neilmarietta.hipchops.util.MessageParser;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {

    @Provides
    @PerActivity
    MessageUseCase provideMessageUseCase(Gson gson, MessageParser messageParser, EmoticonRepository emoticonRepository) {
        return new MessageUseCase(gson, messageParser, emoticonRepository);
    }

    @Provides
    @PerActivity
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @PerActivity
    MessageParser provideMessageParser(WebPageTitleRepository webPageTitleRepository) {
        return new MessageParser(webPageTitleRepository);
    }

    @Provides
    @PerActivity
    WebPageTitleLocalProvider provideWebPageTitleLocalProvider() {
        return new WebPageTitleLocalProvider();
    }
}
