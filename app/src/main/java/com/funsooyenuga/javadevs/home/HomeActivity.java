package com.funsooyenuga.javadevs.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.funsooyenuga.javadevs.R;
import com.funsooyenuga.javadevs.data.User;
import com.funsooyenuga.javadevs.user_detail.UserDetailActivity;
import com.funsooyenuga.javadevs.user_detail.UserDetailFragment;
import com.funsooyenuga.javadevs.util.ActivityUtils;

public class HomeActivity extends AppCompatActivity implements HomeFragment.UserSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // If this is !null, a HomeFragment has been retained already
        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            ActivityUtils.hostFragment(getSupportFragmentManager(), R.id.content_frame, fragment, HomeFragment.TAG);
        }
    }

    @Override
    public void onUserSelected(User user) {
        if (isTwoPane()) {
            UserDetailFragment fragment = UserDetailFragment.newInstance(user.getUsername(),
                    user.getAvatarUrl(), user.getProfileUrl());
            ActivityUtils.hostFragment(getSupportFragmentManager(), R.id.detail_frame, fragment, null);
        } else {
            Intent intent = UserDetailActivity.newIntent(this, user.getUsername(),
                    user.getAvatarUrl(), user.getProfileUrl());
            startActivity(intent);
        }
    }

    /**
     * Checks if the device can support two-pane mode. The detail_frame can only be null
     * if the device is less than 600dp in width
     * @return
     */
    private boolean isTwoPane() {
        return findViewById(R.id.detail_frame) != null;
    }
}
