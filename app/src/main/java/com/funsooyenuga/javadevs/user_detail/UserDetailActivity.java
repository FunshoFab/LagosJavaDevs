package com.funsooyenuga.javadevs.user_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.funsooyenuga.javadevs.R;
import com.funsooyenuga.javadevs.util.ActivityUtils;

public class UserDetailActivity extends AppCompatActivity {

    private static final String USERNAME = "username";
    private static final String AVATAR_URL = "avatar_url";
    private static final String PROFILE_URL = "profile_url";


    public static Intent newIntent(Context context, String username, String avatarUrl, String profileUrl) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(USERNAME, username);
        intent.putExtra(AVATAR_URL, avatarUrl);
        intent.putExtra(PROFILE_URL, profileUrl);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        String username = getIntent().getStringExtra(USERNAME);
        String avatarUrl = getIntent().getStringExtra(AVATAR_URL);
        String profileUrl = getIntent().getStringExtra(PROFILE_URL);

        ActivityUtils.hostFragment(getSupportFragmentManager(), R.id.content_frame,
                UserDetailFragment.newInstance(username, avatarUrl, profileUrl), null);

    }

}
