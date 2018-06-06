package com.example.arnold.takehome.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.arnold.takehome.R;
import com.example.arnold.takehome.databinding.CardviewlayoutBinding;
import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.views.callback.repoClickCallback;

import java.util.List;
import java.util.Objects;

/**
 * Created by ArnoldGoncharenko on 2018-06-05.
 */

public class repoAdapter extends RecyclerView.Adapter<repoAdapter.repoViewHolder>{

    List<? extends repoModel> repoList;

    @Nullable
    private final repoClickCallback projectClickCallback;

    public repoAdapter(@Nullable repoClickCallback repoClickCallback) {
        this.projectClickCallback = repoClickCallback;
    }

    public void setRepoList(final List<? extends repoModel> repoList) {
        if (this.repoList == null) {
            this.repoList = repoList;
            notifyItemRangeInserted(0, repoList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return repoAdapter.this.repoList.size();
                }

                @Override
                public int getNewListSize() {
                    return repoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return repoAdapter.this.repoList.get(oldItemPosition).name.equals(
                            repoList.get(newItemPosition).name);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    repoModel project = repoList.get(newItemPosition);
                    repoModel old = repoList.get(oldItemPosition);

                    if (project.description == null) {
                        project.description = "";
                    }

                    if (old.description == null) {
                        old.description = "";
                    }

                    return project.name.equals(old.name) &&
                            project.description.equals(old.description) &&
                            project.updated_at.equals(old.updated_at) &&
                            project.stargazers_count == old.stargazers_count &&
                            project.forks == old.forks;
                }
            });
            this.repoList = repoList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public repoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardviewlayoutBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.cardviewlayout,
                        parent, false);

        binding.setCallback(projectClickCallback);

        return new repoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(repoViewHolder holder, int position) {
        holder.binding.setRepo(repoList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return repoList == null ? 0 : repoList.size();
    }

    static class repoViewHolder extends RecyclerView.ViewHolder {

        final CardviewlayoutBinding binding;

        public repoViewHolder(CardviewlayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
