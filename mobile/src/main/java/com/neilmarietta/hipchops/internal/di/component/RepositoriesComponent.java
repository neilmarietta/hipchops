package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.internal.di.module.ApiConnectionModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiConnectionModule.class})
public interface RepositoriesComponent {
    EmoticonRepository emoticonRepository();
}
