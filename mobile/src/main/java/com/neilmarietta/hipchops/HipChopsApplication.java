package com.neilmarietta.hipchops;

import android.app.Application;

import com.neilmarietta.hipchops.internal.di.module.AndroidModule;
import com.neilmarietta.hipchops.internal.di.component.ApplicationComponent;
import com.neilmarietta.hipchops.internal.di.component.DaggerApplicationComponent;

public class HipChopsApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
