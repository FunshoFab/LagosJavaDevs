package com.funsooyenuga.javadevs.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FAB THE GREAT on 12/09/2017.
 */

public class Result {

    @SerializedName("total_count") private int totalCount;
    @SerializedName("incomplete_results")private boolean incompleteResults;
    @SerializedName("items")private List<User> users;

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
