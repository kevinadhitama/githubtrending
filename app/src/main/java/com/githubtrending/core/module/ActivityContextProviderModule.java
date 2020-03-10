package com.githubtrending.core.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kevin.adhitama on 10/03/20.
 */
@Module
public class ActivityContextProviderModule {
    private final Context activityContext;

    public ActivityContextProviderModule(Activity activityContext) {
        this.activityContext = activityContext;
    }

    @Provides
    public Context provideActivityContext() {
        return activityContext;
    }
}
