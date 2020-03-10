package com.githubtrending.core.provider;

/**
 * Created by kevin.adhitama on 10/03/20.
 */
public interface TrendingRepoSPProvider {
    String getQ2HCachedTrendingRepoListJSON();

    void setQ2HCachedTrendingRepoListJSON(String stringJSON);
}
