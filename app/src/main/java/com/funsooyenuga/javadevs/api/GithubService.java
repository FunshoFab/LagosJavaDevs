package com.funsooyenuga.javadevs.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FAB THE GREAT on 12/09/2017.
 */

public class GithubService {

    private static final String BASE_URL = "https://api.github.com/";

    public static GithubApi createGithubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        return retrofit.create(GithubApi.class);
    }
}
