package com.githubtrending.page.landing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.githubtrending.R;
import com.githubtrending.databinding.ItemRepositoriesPlaceholderBinding;
import com.githubtrending.databinding.ShimmeringLoadingWidgetBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin.adhitama on 10/03/20.
 */
public class ShimmeringLoadingWidget extends FrameLayout {

    ShimmeringLoadingWidgetBinding mBinding;
    List<ItemRepositoriesPlaceholderBinding> mListPlaceholder;

    public ShimmeringLoadingWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        removeAllViews();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.shimmering_loading_widget, this, false);
        addView(view);

        if (!isInEditMode()) {
            mBinding = DataBindingUtil.bind(view);
            initLoader();
        }
    }

    private void initLoader() {
        mListPlaceholder = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemRepositoriesPlaceholderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_repositories_placeholder, mBinding.shimmerViewContainer, true);
            mListPlaceholder.add(binding);
        }
    }

    public void show() {
        if (isShow()) return;

        for (ItemRepositoriesPlaceholderBinding item : mListPlaceholder) {
            item.shimmerViewContainer.startShimmer();
        }
        setVisibility(VISIBLE);
    }

    public void hide() {
        if (!isShow()) return;

        for (ItemRepositoriesPlaceholderBinding item : mListPlaceholder) {
            item.shimmerViewContainer.stopShimmer();
        }
        setVisibility(GONE);
    }

    private boolean isShow() {
        return getVisibility() == VISIBLE;
    }
}
