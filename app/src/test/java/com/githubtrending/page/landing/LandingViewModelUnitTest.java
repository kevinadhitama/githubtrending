package com.githubtrending.page.landing;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.githubtrending.datamodel.landing.GitHubRepoItem;
import com.githubtrending.page.rule.RxSchedulerRule;
import com.githubtrending.provider.TrendingRepositoriesProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

import io.reactivex.Observable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by kevin.adhitama on 10/03/20.
 */
@RunWith(PowerMockRunner.class)
public class LandingViewModelUnitTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();

    @Mock
    public TrendingRepositoriesProvider mTrendingRepoProvider;

    LandingViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModel = new LandingViewModel(mTrendingRepoProvider);
    }

    @Test
    public void fetchDataLive() {
        when(mTrendingRepoProvider.getRepoList()).thenReturn(Observable.just(createMockResponse()));
        viewModel.fetchData(true);

        //assert
    }

    @Test
    public void fetchDataFromCache() {
        when(mTrendingRepoProvider.getCachedRepoList()).thenReturn(Observable.just(createMockResponse()));
        viewModel.fetchData();

        //assert
    }

    @Test
    public void fetchDataFromEmptyCache() {
        when(mTrendingRepoProvider.getCachedRepoList()).thenReturn(Observable.just(createEmptyMockResponse()));
        doNothing().when(mTrendingRepoProvider).cacheRepoList(createEmptyMockResponse());
        viewModel.fetchData();

        //assert
    }

    @Test
    public void fetchDataDispose() {
        when(mTrendingRepoProvider.getCachedRepoList()).thenReturn(Observable.just(createEmptyMockResponse()));
        doNothing().when(mTrendingRepoProvider).cacheRepoList(createEmptyMockResponse());
        viewModel.fetchData();
        viewModel.fetchData();

        //assert
    }

    @Test
    public void sortDataByStars() {
        PowerMockito.mockStatic(Collections.class);
        when(mTrendingRepoProvider.getRepoList()).thenReturn(Observable.just(createMockResponse()));
        viewModel.fetchData(true);
        viewModel.sortData(LandingViewModel.Sort.BY_STARS);

        //assert
    }

    @Test
    public void sortDataByNames() {
        when(mTrendingRepoProvider.getRepoList()).thenReturn(Observable.just(createMockResponse()));
        viewModel.fetchData(true);
        viewModel.sortData(LandingViewModel.Sort.BY_NAMES);

        //assert
    }

    private GitHubRepoItem[] createEmptyMockResponse() {
        return new GitHubRepoItem[] {};
    }

    private GitHubRepoItem[] createMockResponse() {
        return new GitHubRepoItem[] {
                new GitHubRepoItem("firecracker-microvm",
                        "firecracker",
                        "https://github.com/firecracker-microvm.png",
                        "https://github.com/firecracker-microvm/firecracker",
                        "Secure and fast microVMs for serverless computing.",
                        "Rust",
                        "#dea584",
                        11216,
                        728,
                        277,
                        false),
                new GitHubRepoItem("subnub",
                        "myDrive",
                        "https://github.com/subnub.png",
                        "https://github.com/subnub/myDrive",
                        "Node.js and mongoDB Google Drive Clone",
                        "JavaScript",
                        "#f1e05a",
                        279,
                        37,
                        166,
                        false)
        };
    }
}
