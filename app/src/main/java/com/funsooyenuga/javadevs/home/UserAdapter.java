package com.funsooyenuga.javadevs.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.funsooyenuga.javadevs.R;
import com.funsooyenuga.javadevs.data.User;

import java.util.List;

/**
 * Created by FAB THE GREAT on 13/09/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private Context context;
    private UserClickListener listener;

    public UserAdapter(Context context, List<User> users, UserClickListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_row, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getUsername());
        final ImageView avatar = holder.avatar;

        // Load image into a Circular ImageView
        Glide.with(context)
                .load(user.getAvatarUrl())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(context.getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    public void refreshUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView username;

        UserViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            username = (TextView) itemView.findViewById(R.id.username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = users.get(getAdapterPosition());
                    listener.onUserClick(user);
                }
            });
        }
    }

    interface UserClickListener {

        void onUserClick(User user);
    }
}
