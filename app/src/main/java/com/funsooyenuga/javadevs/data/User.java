package com.funsooyenuga.javadevs.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FAB THE GREAT on 12/09/2017.
 */

public class User {

    @SerializedName("login") private String username;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("html_url") private String profileUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
