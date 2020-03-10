package com.githubtrending.page.landing;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.githubtrending.R;
import com.githubtrending.core.component.DaggerApplicationComponent;
import com.githubtrending.core.module.ActivityContextProviderModule;
import com.githubtrending.databinding.LandingActivityBinding;
import com.githubtrending.datamodel.landing.GitHubRepoItem;
import com.githubtrending.page.landing.adapter.GitHubRepoAdapter;

import java.util.List;

import javax.inject.Inject;

public class LandingActivity extends AppCompatActivity implements Observer<List<GitHubRepoItem>> {

    @Inject
    LandingViewModelFactory mLandingViewModelFactory;
    LandingViewModel mViewModel;
    GitHubRepoAdapter mListAdapter;
    LandingActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplicationComponent.builder()
                .activityContextProviderModule(new ActivityContextProviderModule(this))
                .build()
                .inject(this);

        mViewModel = new ViewModelProvider(this, mLandingViewModelFactory).get(LandingViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.landing_activity);

        initToolbar();
        initLoader();
        initRecyclerView();
        initPullToRefresh();

        //first time load
        if (savedInstanceState == null) mViewModel.fetchData();
    }

    //region init
    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initLoader() {
        for (int i = 0; i < 10; i++)
            getLayoutInflater().inflate(R.layout.item_repositories_placeholder, mBinding.shimmerViewContainer);
    }

    private void removeLoader() {
        mBinding.shimmerViewContainer.removeAllViews();
        mBinding.shimmerViewContainer.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        // Removes blinks
        if (mBinding.recyclerViewRepo.getItemAnimator() != null) {
            ((SimpleItemAnimator) mBinding.recyclerViewRepo.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        mBinding.recyclerViewRepo.addItemDecoration(new DividerItemDecoration(mBinding.recyclerViewRepo.getContext(), DividerItemDecoration.VERTICAL));
        mViewModel.mGithubRepoList.observe(this, this);
    }

    private void initPullToRefresh() {
        mBinding.swipeToRefreshContainer.setOnRefreshListener(() -> mViewModel.fetchData(true));
    }
    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mListAdapter == null) return super.onOptionsItemSelected(item);

        mListAdapter.collapseAll();
        if (R.id.sort_by_stars == item.getItemId()) {
            mViewModel.sortData(LandingViewModel.Sort.BY_STARS);
        } else if (R.id.sort_by_name == item.getItemId()) {
            mViewModel.sortData(LandingViewModel.Sort.BY_NAMES);
        }
        mListAdapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);
    }

    // region on updated data
    @Override
    public void onChanged(List<GitHubRepoItem> repoItems) {
        mListAdapter = new GitHubRepoAdapter(this, repoItems);
        mBinding.recyclerViewRepo.setAdapter(mListAdapter);
        mBinding.swipeToRefreshContainer.setRefreshing(false);
        removeLoader();
    }
    //endregion
}
