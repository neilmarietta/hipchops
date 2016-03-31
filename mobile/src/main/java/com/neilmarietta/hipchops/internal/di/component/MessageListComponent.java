package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.internal.di.module.MessageListModule;
import com.neilmarietta.hipchops.presentation.view.fragment.IOMessageListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MessageListModule.class})
public interface MessageListComponent {
    void inject(IOMessageListFragment fragment);
}
