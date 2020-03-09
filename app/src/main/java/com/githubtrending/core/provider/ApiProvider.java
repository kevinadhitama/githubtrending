package com.githubtrending.core.provider;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
public interface ApiProvider {
    <Res> Observable<Res> get(String url, Map<String, String> query, Class<Res> responseClass);
}
