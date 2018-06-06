package com.example.arnold.takehome.views.UI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arnold.takehome.R;
import com.example.arnold.takehome.databinding.FragmentRepoListBinding;
import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.utils.animationHelper;
import com.example.arnold.takehome.viewModels.listViewModel;
import com.example.arnold.takehome.views.adapter.repoAdapter;
import com.example.arnold.takehome.views.callback.repoClickCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ArnoldGoncharenko on 2018-06-05.
 */

public class RepoListFragment extends Fragment implements LifecycleOwner {
    private repoAdapter projectAdapter;
    private FragmentRepoListBinding binding;
    public listViewModel projectListViewModel;

    private LifecycleRegistry mLifecycleRegistry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list, container, false);

        projectAdapter = new repoAdapter(projectClickCallback);
        binding.repoList.setAdapter(projectAdapter);

        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final listViewModel viewModel =
                ViewModelProviders.of(this).get(listViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(listViewModel viewModel) {
        projectListViewModel = viewModel;
        projectListViewModel.getRepoListObservable().observe(this, new Observer<List<repoModel>>() {
            @Override
            public void onChanged(@Nullable List<repoModel> repos) {
                //ArrayList<repoModel> newRepos = new ArrayList<>();
                //newRepos.add(new repoModel("Test", "testest", "testDate", 0, 0));
                //newRepos.add(new repoModel("Test1", "testest1", "testDate1", 20, 5));
                //newRepos.add(new repoModel("Test2", "testest2", "testDate2", 40, 10));
                if (repos != null) {
                    animationHelper.animateLayer(0, 1, getActivity().findViewById(R.id.repo_list), 1000);
                    animationHelper.moveObjectIntoView(100, 0, getActivity().findViewById(R.id.repo_list), 600);

                    projectAdapter.setRepoList(repos);
                }
            }
        });
    }

    public void updateRecycler(String username) {
        projectListViewModel.populateRepoList(username);
        final listViewModel viewModel =
                ViewModelProviders.of(this).get(listViewModel.class);

        observeViewModel(viewModel);
    }


    private final repoClickCallback projectClickCallback = new repoClickCallback() {
        @Override
        public void onClick(repoModel repoModel) {
            animationHelper.animateLayer(0, 1, getActivity().findViewById(R.id.constraintLayout), 200);
            animationHelper.moveObjectIntoView(200, 0, getActivity().findViewById(R.id.cv_repo_info), 200);

            TextView lastUpdatedText = (TextView) getActivity().findViewById(R.id.lblLastUpdatedValue);
            TextView starsText = (TextView) getActivity().findViewById(R.id.lblStarsValue);
            TextView forksText = (TextView) getActivity().findViewById(R.id.lblForksValue);

            SimpleDateFormat spf=new SimpleDateFormat("yyyy-mm-dd'T'kk:mm:ss'Z'");

            Date newDate = null;
            try {
                newDate = spf.parse(repoModel.updated_at);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat dt = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
            String date = dt.format(newDate);

            lastUpdatedText.setText(date);
            starsText.setText(String.valueOf(repoModel.stargazers_count));
            forksText.setText(String.valueOf(repoModel.forks));
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
