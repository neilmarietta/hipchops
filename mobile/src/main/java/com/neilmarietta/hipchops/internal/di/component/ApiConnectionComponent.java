package com.neilmarietta.hipchops.internal.di.component;

import com.neilmarietta.hipchops.data.cache.EmoticonCache;
import com.neilmarietta.hipchops.internal.di.module.ApiConnectionModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApiConnectionModule.class})
public interface ApiConnectionComponent {
    // Provide instance for downstream components
    Retrofit retrofit();
    EmoticonCache emoticonCache();
}
