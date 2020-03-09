package com.githubtrending.page.landing;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.githubtrending.provider.TrendingRepositoriesProvider;

import javax.inject.Inject;

/**
 * Created by kevin.adhitama on 09/03/20.
 */
public class LandingViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final TrendingRepositoriesProvider mTrendingRepositoriesProvider;

    @Inject
    public LandingViewModelFactory(TrendingRepositoriesProvider trendingRepositoriesProvider) {
        mTrendingRepositoriesProvider = trendingRepositoriesProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (LandingViewModel.class.isAssignableFrom(modelClass)) {
            //noinspection unchecked
            return (T) new LandingViewModel(mTrendingRepositoriesProvider);
        }
        return super.create(modelClass);
    }
}
