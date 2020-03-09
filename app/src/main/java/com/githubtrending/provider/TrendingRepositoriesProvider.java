package com.githubtrending.provider;

import com.githubtrending.core.provider.ApiProvider;
import com.githubtrending.core.provider.ApiRoute;
import com.githubtrending.datamodel.landing.GitHubRepoItem;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
public class TrendingRepositoriesProvider {
    private ApiProvider mApiProvider;
    private ApiRoute mApiRoute;

    @Inject
    public TrendingRepositoriesProvider(ApiProvider mApiProvider, ApiRoute mApiRoute) {
        this.mApiProvider = mApiProvider;
        this.mApiRoute = mApiRoute;
    }

    public Observable<GitHubRepoItem[]> getRepoList() {
        return mApiProvider.get(mApiRoute.getTrendingRepositoriesListUrl(), new HashMap<>(), GitHubRepoItem[].class);
    }
}
