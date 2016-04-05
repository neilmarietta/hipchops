package com.neilmarietta.hipchops;

import android.app.Application;

import com.neilmarietta.hipchops.internal.di.component.ApiConnectionComponent;
import com.neilmarietta.hipchops.internal.di.component.DaggerApiConnectionComponent;
import com.neilmarietta.hipchops.internal.di.module.AndroidModule;
import com.neilmarietta.hipchops.internal.di.component.ApplicationComponent;
import com.neilmarietta.hipchops.internal.di.component.DaggerApplicationComponent;

public class HipChopsApplication extends Application {

    private ApplicationComponent mApplicationComponent;
    private ApiConnectionComponent mApiConnectionComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();

        mApiConnectionComponent = DaggerApiConnectionComponent.create();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public ApiConnectionComponent getApiConnectionComponent() {
        return mApiConnectionComponent;
    }
}
