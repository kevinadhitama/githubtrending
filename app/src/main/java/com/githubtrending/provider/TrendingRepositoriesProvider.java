package com.githubtrending.provider;

import com.githubtrending.core.provider.ApiProvider;
import com.githubtrending.core.provider.ApiRoute;
import com.githubtrending.core.provider.TrendingRepoSPProvider;
import com.githubtrending.datamodel.landing.GitHubRepoItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
public class TrendingRepositoriesProvider {
    private ApiProvider mApiProvider;
    private ApiRoute mApiRoute;
    private TrendingRepoSPProvider mTrendingRepoSPProvider;

    @Inject
    public TrendingRepositoriesProvider(ApiProvider apiProvider, ApiRoute apiRoute, TrendingRepoSPProvider trendingRepoSPProvider) {
        mApiProvider = apiProvider;
        mApiRoute = apiRoute;
        mTrendingRepoSPProvider = trendingRepoSPProvider;
    }

    public Observable<GitHubRepoItem[]> getRepoList() {
        return mApiProvider.get(mApiRoute.getTrendingRepositoriesListUrl(), new HashMap<>(), GitHubRepoItem[].class);
    }

    public Observable<GitHubRepoItem[]> getCachedRepoList() {
        String cachedTrendingRepoJSON = mTrendingRepoSPProvider.getQ2HCachedTrendingRepoListJSON();
        if (cachedTrendingRepoJSON.isEmpty()) {
            return Observable.just(new GitHubRepoItem[]{});
        }

        Gson gson = new GsonBuilder().create();
        return Observable.just(gson.fromJson(cachedTrendingRepoJSON, GitHubRepoItem[].class));
    }

    public void cacheRepoList(GitHubRepoItem[] repoItems) {
        Gson gson = new GsonBuilder().create();
        mTrendingRepoSPProvider.setQ2HCachedTrendingRepoListJSON(gson.toJson(repoItems));
    }
}
