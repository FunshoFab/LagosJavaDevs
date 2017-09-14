package com.funsooyenuga.javadevs.home;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.funsooyenuga.javadevs.R;
import com.funsooyenuga.javadevs.api.GithubApi;
import com.funsooyenuga.javadevs.api.GithubService;
import com.funsooyenuga.javadevs.data.Result;
import com.funsooyenuga.javadevs.data.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private static final String GITHUB_QUERY = "type:user language:java location:lagos";
    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar progress;

    private UserAdapter adapter;

    private CompositeDisposable disposable = new CompositeDisposable();

    private UserSelectedListener userSelectedListener;

    private List<User> users = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            try {
                userSelectedListener = (UserSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() +
                        " must implement HomeFragment.UserSelectedListener");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment when there is a config change
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.users_rv);
        progress = (ProgressBar) v.findViewById(R.id.progress_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());

        adapter = new UserAdapter(getActivity(), users, adapterListener);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        if (users.isEmpty()) {
            fetchJavaDevs();
        }

        return v;
    }

    private void fetchJavaDevs() {
        progress.setVisibility(View.VISIBLE);

        GithubApi github = GithubService.createGithubService();

        disposable.add(
                github.getJavaDevs(GITHUB_QUERY)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<Result>() {
                            @Override
                            public void onNext(Result value) {
                                users = value.getUsers();
                                showResult();
                            }

                            @Override
                            public void onError(Throwable e) {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onComplete() {}
                        })
        );
    }

    private void showResult() {
        progress.setVisibility(View.GONE);
        adapter.refreshUsers(users);
    }

    @Override
    public void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }

    UserAdapter.UserClickListener adapterListener = new UserAdapter.UserClickListener() {
        @Override
        public void onUserClick(User user) {
            userSelectedListener.onUserSelected(user);
        }
    };

    public interface UserSelectedListener {

        void onUserSelected(User user);
    }
}
