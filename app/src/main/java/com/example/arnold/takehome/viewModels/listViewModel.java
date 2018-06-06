package com.example.arnold.takehome.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.services.githubImplementation;

import java.util.List;

/**
 * Created by Arnold on 6/4/2018.
 */

public class listViewModel extends AndroidViewModel {
    private LiveData<List<repoModel>> repoListObservable;

    public listViewModel(Application application) {
        super(application);

        // If any transformation is needed, this can be simply done by Transformations class ...
        repoListObservable = githubImplementation.getInstance().getUserRepos("");
    }

    public void populateRepoList(String username) {
        repoListObservable =  githubImplementation.getInstance().getUserRepos(username);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<repoModel>> getRepoListObservable() {
        return repoListObservable;
    }
}
