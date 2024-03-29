package com.githubtrending.page.landing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.githubtrending.R;
import com.githubtrending.databinding.ItemRepositoriesBinding;
import com.githubtrending.datamodel.landing.GitHubRepoItem;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by kevin.adhitama on 09/03/20.
 */
public class GitHubRepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GitHubRepoItem> mGitHubRepoItemList;
    private int mSelectedItem = -1;

    public GitHubRepoAdapter(Context context, List<GitHubRepoItem> gitHubRepoItemList) {
        mContext = context;
        mGitHubRepoItemList = gitHubRepoItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.item_repositories, parent, false);
        return new ItemViewHolder(itemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemRepositoriesBinding binding = (ItemRepositoriesBinding) ((ItemViewHolder) holder).getDataBinding();

            GitHubRepoItem item = mGitHubRepoItemList.get(position);
            binding.textViewAuthor.setText(item.getAuthor());
            binding.textViewRepoName.setText(item.getName());
            binding.textViewRepoDesc.setText(item.getDescription());
            binding.textViewCodeLanguage.setText(item.getLanguage());
            if (item.getLanguageColor() != null && !item.getLanguageColor().isEmpty()) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(mContext, R.drawable.ic_code_language_indicator_16dp);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.getLanguageColor()));
                binding.textViewCodeLanguage.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null);
            }
            binding.textViewForked.setText(String.valueOf(item.getForks()));
            binding.textViewStarred.setText(String.valueOf(item.getStars()));

            setDetailGroupVisibility(binding, item.isExpanded() ? View.VISIBLE : View.GONE);
            binding.textViewCodeLanguage.setVisibility(item.getLanguage() != null && item.isExpanded() ? View.VISIBLE : View.GONE);
            Glide.with(mContext)
                    .load(item.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .transition(withCrossFade())
                    .into(binding.imageViewAvatar);

            binding.getRoot().setOnClickListener(v -> {
                item.setExpanded(!item.isExpanded());
                notifyItemChanged(position);

                if (mSelectedItem != -1) {
                    mGitHubRepoItemList.get(mSelectedItem).setExpanded(false);
                    notifyItemChanged(mSelectedItem);
                }

                TransitionManager.beginDelayedTransition(binding.container);
                mSelectedItem = item.isExpanded() ? position : -1;
            });
        }
    }

    private void setDetailGroupVisibility(ItemRepositoriesBinding binding, int visibility) {
        binding.textViewRepoDesc.setVisibility(visibility);
        binding.textViewCodeLanguage.setVisibility(visibility);
        binding.textViewStarred.setVisibility(visibility);
        binding.textViewForked.setVisibility(visibility);
    }

    public void collapseAll() {
        if (mSelectedItem == -1) return;

        mGitHubRepoItemList.get(mSelectedItem).setExpanded(false);
        mSelectedItem = -1;
    }

    @Override
    public int getItemCount() {
        return mGitHubRepoItemList.size();
    }

    //region ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ViewDataBinding getDataBinding() {
            return DataBindingUtil.getBinding(itemView);
        }
    }
    //endregion
}
