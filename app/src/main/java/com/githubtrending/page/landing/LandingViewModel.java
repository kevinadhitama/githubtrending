package com.githubtrending.page.landing;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.githubtrending.datamodel.landing.GitHubRepoItem;
import com.githubtrending.provider.TrendingRepositoriesProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
public class LandingViewModel extends ViewModel {

    enum Sort {
        BY_STARS,
        BY_NAMES
    }

    private CompositeDisposable mCompositeDisposable;
    private Disposable mDisposable;
    private TrendingRepositoriesProvider mTrendingRepoProvider;
    MutableLiveData<List<GitHubRepoItem>> mGithubRepoList;
    MutableLiveData<Boolean> mLoadingState;
    String errorMessage;
    Boolean needCacheRepoList;

    public LandingViewModel(TrendingRepositoriesProvider trendingRepoProvider) {
        mTrendingRepoProvider = trendingRepoProvider;
        mCompositeDisposable = new CompositeDisposable();
        mGithubRepoList = new MutableLiveData<>();
        mLoadingState = new MutableLiveData<>();
    }

    void fetchData() {
        fetchData(false);
    }

    void fetchData(boolean reloadLiveData) {
        if (mDisposable != null) {
            mDisposable.dispose();
        }

        if (reloadLiveData) {
            fetchDataLive();
        } else {
            fetchDataFromCache();
        }

        mCompositeDisposable.add(mDisposable);
    }

    private void fetchDataFromCache() {
        mDisposable = mTrendingRepoProvider.getCachedRepoList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mLoadingState.postValue(true);
                }).flatMap(res -> {
                    if (res.length == 0) {
                        needCacheRepoList = true;
                        return mTrendingRepoProvider.getRepoList();
                    }
                    needCacheRepoList = false;
                    return Observable.just(res);
                }).subscribe(res -> {
                    if (needCacheRepoList) {
                        mTrendingRepoProvider.cacheRepoList(res);
                    }
                    mGithubRepoList.postValue(Arrays.asList(res));
                    mLoadingState.postValue(false);
                }, err -> {
                    errorMessage = err.getMessage();
                    mLoadingState.postValue(false);
                });
    }

    private void fetchDataLive() {
        mDisposable = mTrendingRepoProvider.getRepoList()
                .subscribeOn(Schedulers.io())
                .subscribe(res -> {
                            mTrendingRepoProvider.cacheRepoList(res);
                            mGithubRepoList.setValue(Arrays.asList(res));
                            mLoadingState.setValue(false);
                        },
                        err -> {
                            errorMessage = err.getMessage();
                            mLoadingState.postValue(false);
                        });
    }

    void sortData(Sort sort) {
        if (mGithubRepoList.getValue() == null || mGithubRepoList.getValue().isEmpty()) return;

        Comparator<GitHubRepoItem> comparator;
        switch (sort) {
            case BY_STARS:
                comparator = (o1, o2) -> o2.getStars().compareTo(o1.getStars());
                break;
            case BY_NAMES:
                comparator = (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sort);
        }

        Collections.sort(mGithubRepoList.getValue(), comparator);
    }
}
