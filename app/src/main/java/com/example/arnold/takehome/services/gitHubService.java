package com.example.arnold.takehome.services;

import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.models.userModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ArnoldGoncharenko on 2018-06-05.
 */

interface gitHubService {
    String githubUrl = "https://api.github.com/users/";

    @GET("{user}")
    Call<userModel> getUser(@Path("user") String user);

    @GET("{user}/repos")
    Call<List<repoModel>> getUserRepos(@Path("user") String user);
}
