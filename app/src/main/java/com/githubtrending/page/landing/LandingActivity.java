package com.githubtrending.page.landing;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.githubtrending.R;
import com.githubtrending.core.component.DaggerApplicationComponent;

import javax.inject.Inject;

public class LandingActivity extends AppCompatActivity {

    @Inject
    LandingViewModelFactory mLandingViewModelFactory;
    LandingViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplicationComponent.builder().build().inject(this);

        mViewModel = new ViewModelProvider(this, mLandingViewModelFactory).get(LandingViewModel.class);

        setContentView(R.layout.landing_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);

        mViewModel.fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.sort_by_stars == item.getItemId()) {
            //todo impl sort stars
        } else if (R.id.sort_by_name == item.getItemId()) {
            //todo impl sort name
        }
        return super.onOptionsItemSelected(item);
    }
}
