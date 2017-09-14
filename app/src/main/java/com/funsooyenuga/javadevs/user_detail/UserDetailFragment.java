package com.funsooyenuga.javadevs.user_detail;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.funsooyenuga.javadevs.R;

public class UserDetailFragment extends Fragment implements View.OnClickListener {

    private static final String USERNAME = "username";
    private static final String AVATAR_URL = "avatar_url";
    private static final String PROFILE_URL = "profile_url";

    private String username;
    private String avatarUrl;
    private String profileUrl;

    private TextView username_tv;
    private ImageView avatar_img;
    private Button viewProfile_btn;
    private ProgressBar progressBar;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(String username, String avatarUrl, String profileUrl) {
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(AVATAR_URL, avatarUrl);
        args.putString(PROFILE_URL, profileUrl);

        UserDetailFragment fragment = new UserDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = getArguments().getString(USERNAME);
        avatarUrl = getArguments().getString(AVATAR_URL);
        profileUrl = getArguments().getString(PROFILE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_detail, container, false);

        username_tv = (TextView) v.findViewById(R.id.username_tv);
        avatar_img = (ImageView) v.findViewById(R.id.avatar_img);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        viewProfile_btn = (Button) v.findViewById(R.id.view_profile_btn);
        viewProfile_btn.setOnClickListener(this);

        username_tv.setText(username);
        loadAvatar(avatarUrl);

        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl));
        startActivity(intent);
    }

    private void loadAvatar(String avatarUrl) {
        Glide.with(getActivity())
                .load(avatarUrl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.ic_face_black_48dp)
                .listener(listener)
                .into(new BitmapImageViewTarget(avatar_img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(getActivity().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        avatar_img.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    RequestListener listener = new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), R.string.avatar_load_error, Toast.LENGTH_LONG).show();
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target,
                                       boolean isFromMemoryCache, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            String message = "Checkout this awesome developer @" + username + " " + profileUrl;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(intent);

            return true;
        }
        return false;
    }
}
