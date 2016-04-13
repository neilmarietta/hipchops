package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.interactor.MessageUseCaseTest;
import com.neilmarietta.hipchops.internal.di.PerTest;
import com.neilmarietta.hipchops.internal.di.module.TestMessageModule;

import dagger.Component;

@PerTest
@Component(modules = {TestMessageModule.class})
public interface TestMessageComponent {
    void inject(MessageUseCaseTest test);
}
