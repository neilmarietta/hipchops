package com.neilmarietta.hipchops.internal.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module
public class AndroidModule {

    private final Application mApplication;

    public AndroidModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}