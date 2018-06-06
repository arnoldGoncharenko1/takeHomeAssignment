package com.example.arnold.takehome.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.models.userModel;
import com.example.arnold.takehome.services.githubImplementation;

import java.util.List;

public class userViewModel extends AndroidViewModel {
    private LiveData<userModel> userObservable;

    public userViewModel(@NonNull Application application) {
        super(application);

        userObservable = githubImplementation.getInstance().getUser("");
    }

    public void getUser(String username) {
        userObservable =  githubImplementation.getInstance().getUser(username);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<userModel> getUserObservable() {
        return userObservable;
    }
}
