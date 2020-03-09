package com.githubtrending.core.provider;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
public class ApiRouteImpl implements ApiRoute {
    @Override
    public String getTrendingRepositoriesListUrl() {
        return "https://github-trending-api.now.sh/repositories?language=&since=daily";
    }
}
