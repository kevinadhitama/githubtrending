package com.githubtrending.core.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
public class ApiProviderImpl implements ApiProvider {
    private OkHttpClient client = new OkHttpClient();

    @Override
    public <Res> Observable<Res> get(String url, Map<String, String> query, Class<Res> responseClass) {
        return Observable.<Res>create(subscriber -> {
            try {
                HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
                if (query != null) {
                    for (Map.Entry<String, String> param : query.entrySet()) {
                        httpBuilder.addQueryParameter(param.getKey(), param.getValue());
                    }
                }
                Response response = client.newCall(new Request.Builder()
                        .url(httpBuilder.build()).build()).execute();

                if (response.isSuccessful()) {
                    if (!subscriber.isDisposed()) {
                        Gson gson = new GsonBuilder().create();
                        Res res = gson.fromJson(response.body().string(), responseClass);
                        subscriber.onNext(res);
                    }
                    subscriber.onComplete();
                } else if (!response.isSuccessful() && !subscriber.isDisposed()) {
                    subscriber.onError(new Exception("error"));
                }
            } catch (NullPointerException e) {
                if (!subscriber.isDisposed()) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
