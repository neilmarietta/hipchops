package com.neilmarietta.hipchops.internal.di.component;

import android.app.Application;

import com.neilmarietta.hipchops.internal.di.module.AndroidModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class})
public interface ApplicationComponent {
    Application application();
}
