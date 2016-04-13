package com.neilmarietta.hipchops.internal.di.module;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.interactor.MessageUseCase;
import com.neilmarietta.hipchops.internal.di.PerTest;
import com.neilmarietta.hipchops.util.MessageParser;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestMessageModule {

    @Provides
    @PerTest
    MessageUseCase provideMessageUseCase(Gson gson, MessageParser messageParser, EmoticonRepository emoticonRepository) {
        return new MessageUseCase(gson, messageParser, emoticonRepository);
    }

    @Provides
    @PerTest
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @PerTest
    MessageParser provideMessageParser() {
        return mock(MessageParser.class);
    }

    @Provides
    @PerTest
    EmoticonRepository provideEmoticonRepository() {
        return mock(EmoticonRepository.class);
    }
}
