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
import java.util.Collections;

public class LoadUserPostsVideos extends RecyclerView.ViewHolder {

    StyledPlayerView playerView;
    ExoPlayer exoplayer;

    public LoadUserPostsVideos(@NonNull View itemView) {
        super(itemView);
    }

    public void setPost(Activity activity, String name, String url, String postUri, String time, String uid, String type, String desc, String username, String postid) {

        playerView = itemView.findViewById(R.id.userpostvv);
        exoplayer = new ExoPlayer.Builder(activity).build();
        playerView.setPlayer(exoplayer);
        MediaItem mediaItem = MediaItem.fromUri(postUri);
        Log.e("Video Load", postUri);
        exoplayer.addMediaItems(Collections.singletonList(mediaItem));
        exoplayer.setPlayWhenReady(false);
        exoplayer.prepare();

    }

}
