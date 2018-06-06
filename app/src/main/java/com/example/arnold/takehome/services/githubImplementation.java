package com.example.arnold.takehome.services;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.models.userModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ArnoldGoncharenko on 2018-06-05.
 */

public class githubImplementation {
    private gitHubService gitHubService;
    private static githubImplementation gitHubServiceImpl;

    private githubImplementation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(gitHubService.githubUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubService = retrofit.create(gitHubService.class);
    }

    public synchronized static githubImplementation getInstance() {
        if (gitHubServiceImpl == null) {
            if (gitHubServiceImpl == null) {
                gitHubServiceImpl = new githubImplementation();
            }
        }
        return gitHubServiceImpl;
    }

    public LiveData<userModel> getUser(String username) {
        final MutableLiveData<userModel> data = new MutableLiveData<>();

        gitHubService.getUser(username).enqueue(new Callback<userModel>() {
            @Override
            public void onResponse(Call<userModel> call, Response<userModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<userModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<repoModel>> getUserRepos(String username) {
        final MutableLiveData<List<repoModel>> data = new MutableLiveData<>();

        gitHubService.getUserRepos(username).enqueue(new Callback<List<repoModel>>() {
            @Override
            public void onResponse(Call<List<repoModel>> call, Response<List<repoModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<repoModel>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
