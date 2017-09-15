package com.funsooyenuga.javadevs.data;

import com.funsooyenuga.javadevs.api.GithubApi;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by FAB THE GREAT on 15/09/2017.
 */

public class UserRepository {

    private static UserRepository instance;

    private Result cachedResult;

    private UserRepository() {
        // Prevent external instantiation
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public Observable<Result> getJavaDevs(String query, GithubApi github) {
        if (cachedResult != null) {
            return Observable.just(cachedResult);
        } else {
            // Fetch result from API and cache it
            return github.getJavaDevs(query)
                    .doOnNext(new Consumer<Result>() {
                        @Override
                        public void accept(Result result) throws Exception {
                            cachedResult = result;
                        }
                    });
        }
    }
}
