package com.githubtrending.page.landing;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.githubtrending.R;
import com.githubtrending.core.component.DaggerApplicationComponent;
import com.githubtrending.databinding.LandingActivityBinding;
import com.githubtrending.page.landing.adapter.GitHubRepoAdapter;

import javax.inject.Inject;

public class LandingActivity extends AppCompatActivity {

    @Inject
    LandingViewModelFactory mLandingViewModelFactory;
    LandingViewModel mViewModel;
    GitHubRepoAdapter mListAdapter;
    LandingActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplicationComponent.builder().build().inject(this);

        mViewModel = new ViewModelProvider(this, mLandingViewModelFactory).get(LandingViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.landing_activity);

        initToolbar();
        initRecyclerView();
        initPullToRefresh();

        //first time load
        if (savedInstanceState == null) mViewModel.fetchData();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initRecyclerView() {
        // Removes blinks
        if (mBinding.recyclerViewRepo.getItemAnimator() != null) {
            ((SimpleItemAnimator) mBinding.recyclerViewRepo.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        mBinding.recyclerViewRepo.addItemDecoration(new DividerItemDecoration(mBinding.recyclerViewRepo.getContext(), DividerItemDecoration.VERTICAL));
        mViewModel.mGithubRepoList.observe(this, gitHubRepoItems -> {
            mListAdapter = new GitHubRepoAdapter(this, gitHubRepoItems);
            mBinding.recyclerViewRepo.setAdapter(mListAdapter);
            mBinding.swipeToRefreshContainer.setRefreshing(false);
        });
    }

    private void initPullToRefresh() {
        mBinding.swipeToRefreshContainer.setOnRefreshListener(() -> mViewModel.fetchData());
    }

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
}
