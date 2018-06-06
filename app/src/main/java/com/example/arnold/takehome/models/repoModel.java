package com.example.arnold.takehome.models;

/**
 * Created by Arnold on 6/4/2018.
 */

public class repoModel {
    public String name;
    public String description;
    public String updated_at;
    public int stargazers_count;
    public int forks;

    public repoModel(String name, String desc, String updatedAt, int stargazersCount, int forks) {
        this.name = name;
        this.description = desc;
        this.updated_at = updatedAt;
        this.stargazers_count = stargazersCount;
        this.forks = forks;
    }
}
