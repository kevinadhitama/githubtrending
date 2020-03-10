package com.githubtrending.core.module;

import android.content.Context;

import com.githubtrending.core.provider.ApiProvider;
import com.githubtrending.core.provider.ApiProviderImpl;
import com.githubtrending.core.provider.ApiRoute;
import com.githubtrending.core.provider.ApiRouteImpl;
import com.githubtrending.core.provider.TrendingRepoSPProvider;
import com.githubtrending.core.provider.TrendingRepoSPProviderImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */

@Module
public class AppProviderModule {

    @Singleton
    @Provides
    ApiProvider providesApiRepository() {
        return new ApiProviderImpl();
    }

    @Singleton
    @Provides
    ApiRoute providesApiRoute() {
        return new ApiRouteImpl();
    }

    @Singleton
    @Provides
    TrendingRepoSPProvider providesTrendingRepoSP(Context context) {
        return new TrendingRepoSPProviderImpl(context);
    }
}
