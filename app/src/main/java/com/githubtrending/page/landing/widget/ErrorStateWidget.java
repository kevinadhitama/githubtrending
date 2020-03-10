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
import com.githubtrending.databinding.ErrorStateWidgetBinding;

/**
 * Created by kevin.adhitama on 10/03/20.
 */
public class ErrorStateWidget extends FrameLayout {

    ErrorStateWidgetBinding mBinding;
    Listener mListener;

    public ErrorStateWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        removeAllViews();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.error_state_widget, this, false);
        addView(view);

        if (!isInEditMode()) {
            mBinding = DataBindingUtil.bind(view);
            initListener();
            hide();
        }
    }

    private void initListener() {
        mBinding.buttonRetry.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onRetryClickListener();
            }
        });
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public interface Listener {
        void onRetryClickListener();
    }
}
