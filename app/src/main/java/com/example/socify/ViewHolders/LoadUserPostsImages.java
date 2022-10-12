package com.example.socify.ViewHolders;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.squareup.picasso.Picasso;
import java.util.Collections;

public class LoadUserPostsImages extends RecyclerView.ViewHolder {

    ImageView userpostiv;
    StyledPlayerView playerView;


    public LoadUserPostsImages(@NonNull View itemView) {
        super(itemView);
    }

    public void setPost(Activity activity, String name, String url, String postUri, String time, String uid, String type, String desc, String username, String postiduser, String postidall) {

        userpostiv = itemView.findViewById(R.id.userpostiv);
        playerView = itemView.findViewById(R.id.userpostvv);

            Picasso.get().load(postUri).into(userpostiv);
            userpostiv.setVisibility(View.VISIBLE);
        Log.e("Image", postUri);

    }

}
