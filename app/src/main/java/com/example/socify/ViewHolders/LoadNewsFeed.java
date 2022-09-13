package com.example.socify.ViewHolders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.prnd.readmore.ReadMoreTextView;

public class LoadNewsFeed extends RecyclerView.ViewHolder {

    CircleImageView profilepic;
    ImageView post;
    MaterialTextView namepost, usernamepost, date,likescount;
    public ImageView like, comment;
    ReadMoreTextView description;
    DatabaseReference likesref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    int likecount;
    StyledPlayerView playerView;
    ExoPlayer exoplayer;


    public LoadNewsFeed(@NonNull View itemView) {
        super(itemView);
    }

    public void setPost(Activity activity, String name, String url, String postUri, String time, String uid, String type, String desc, String username) {
        profilepic = itemView.findViewById(R.id.ProfilePic);
        post = itemView.findViewById(R.id.post);
        namepost = itemView.findViewById(R.id.picadded);
        date = itemView.findViewById(R.id.time);
        like = itemView.findViewById(R.id.like);
//        comment = itemView.findViewById(R.id.comment);
        likescount = itemView.findViewById(R.id.likecount);
        description = itemView.findViewById(R.id.caption);
        usernamepost = itemView.findViewById(R.id.usernamepost);
        playerView = itemView.findViewById(R.id.videopost);

        if(type.equals("image")) {
            Picasso.get().load(url).into(profilepic);
            Picasso.get().load(postUri).into(post);
            namepost.setText(name);
            date.setText(time);
            usernamepost.setText(username);
            description.setText(desc);
            playerView.setVisibility(View.INVISIBLE);
            post.setVisibility(View.VISIBLE);
        }
        else if(type.equals("video")) {
            Picasso.get().load(url).into(profilepic);
            namepost.setText(name);
            date.setText(time);
            usernamepost.setText(username);
            playerView.setVisibility(View.VISIBLE);
            description.setText(desc);
            post.setVisibility(View.INVISIBLE);
            try {
                exoplayer = new ExoPlayer.Builder(activity).build();
                playerView.setPlayer(exoplayer);
                MediaItem mediaItem = MediaItem.fromUri(postUri);
                exoplayer.addMediaItems(Collections.singletonList(mediaItem));
                exoplayer.setPlayWhenReady(false);
                exoplayer.prepare();

            }catch (Exception e) {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void likechecker(final String postkey) {
        like = itemView.findViewById(R.id.like);
        likesref = database.getReference("Likes");
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        likesref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postkey).hasChild(currentUID)) {
                    like.setImageResource(R.drawable.ic_eye_close);
                    likecount = (int) snapshot.child(postkey).getChildrenCount();
                    likescount.setText(Integer.toString(likecount) + " thunders");
                }
                else{
                    like.setImageResource(R.drawable.ic_eye);
                    likecount = (int) snapshot.child(postkey).getChildrenCount();
                    likescount.setText(Integer.toString(likecount) + " thunders");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
