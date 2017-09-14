package com.funsooyenuga.javadevs.api;

import com.funsooyenuga.javadevs.data.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by FAB THE GREAT on 12/09/2017.
 */

public interface GithubApi {

    @GET("search/users")
    Observable<Result> getJavaDevs(@Query("q") String query);
}
