package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.internal.di.PerMessage;
import com.neilmarietta.hipchops.internal.di.module.MessageModule;
import com.neilmarietta.hipchops.presentation.view.fragment.IOMessageListFragment;

import dagger.Component;

@PerMessage
@Component(dependencies = {ApiConnectionComponent.class}, modules = {MessageModule.class})
public interface MessageComponent {
    void inject(IOMessageListFragment fragment);
}
