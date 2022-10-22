package com.example.socify.HomeFragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.ChatRoom;
import com.example.socify.Activities.Home;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.InterfaceClass;
import com.example.socify.R;
import com.example.socify.databinding.FragmentVisitProfileBinding;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firestore.v1.WriteResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class VisitProfile extends Fragment   {

    GetUserData getUserData;
    String uid ;
    boolean followstatus;
    FragmentVisitProfileBinding binding;
    AppCompatButton followButton;

    CircleImageView profilePhoto;
    MaterialTextView username,name;
    ChipGroup group;
    TextView followercountView;
    TextView followingCountView;

    CountDownTimer timer;
    public VisitProfile(String uid ){
        Log.i("person uid", uid);
        this.uid  = uid;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePhoto = getView().findViewById(R.id.profile_pic);
        username= getView().findViewById(R.id.username_profile);
        name = getView().findViewById(R.id.name);
        group = getView().findViewById(R.id.chip_group);
        followercountView = getView().findViewById(R.id.followerscount);
        followingCountView = getView().findViewById(R.id.followingcount);
        followButton = getView().findViewById(R.id.follow);



        getUserData = new GetUserData(uid, new InterfaceClass.VisitProfileInterface() {
            @Override
            public void onWorkDone() {
                if (getContext() != null) {
                    Glide.with(getContext()).load(getUserData.imgurl).placeholder(R.drawable.user).into(profilePhoto);
                    username.setText(getUserData.username);
                    name.setText(getUserData.name);
                    followercountView.setText(getUserData.followerscount);
                    followingCountView.setText(getUserData.followingcount);

                    for (String s : getUserData.tags) {
                        Chip chip = new Chip(requireActivity());
                        chip.setText(s);
                        chip.setElevation(5);
                        chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.black)));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.palette_light_teal)));
                        group.addView(chip);

                    }

                    if(Home.getUserData.followinglistuids.contains(uid)){
                        followButton.setText("Following");
                        followstatus = true;
                    }else
                        followstatus = false;

                    if (getUserData.profilestatus.equals("private")) {
                        getView().findViewById(R.id.privatemessage).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        setOnclickListeners();
    }

    private void setOnclickListeners() {

        binding.message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ChatRoom.class);
                intent.putExtra("Name", getUserData.name);
                intent.putExtra("Img", getUserData.imgurl);
                intent.putExtra("UID", getUserData.uid);
                startActivity(intent);
            }
        });


        binding.backbtnvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, new DiscoverFragment()).commit();
            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(followstatus){
                    followstatus = false;
                    followButton.setText("Follow");
                    followercountView.setText(String.valueOf(Integer.parseInt(followercountView.getText().toString()) -1));
                }else{
                    followstatus = true;
                    followButton.setText("Following");
                    followercountView.setText(String.valueOf(Integer.parseInt(followercountView.getText().toString()) +1));
                }
                if(timer!=null){
                    timer.cancel();
                }else{
                    timer = new CountDownTimer(500,500) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            if(followstatus)
                                followUser();
                            else
                                unfollowuser();
                            timer = null;
                        }
                    }.start();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getUserData = null;
    }

    private void unfollowuser() {
        followButton.setText("Follow");
        Home.getUserData.followinglistuids.remove(uid);
        getUserData.followerslistuids.remove(Home.getUserData.uid);
        followstatus = false;

        getUserData.snap.getReference().update("FollowersCount",followercountView.getText());
        Home.getUserData.snap.getReference().update("FollowingCount",String.valueOf(Integer.parseInt(Home.getUserData.followingcount) -1));

        updateStatus();
    }

    private void updateStatus(){
        HashMap<String,ArrayList<String>> mp = new HashMap<>();
        mp.put("Followinglist",getUserData.followinglistuids);
        mp.put("FollowersList",getUserData.followerslistuids);

        HashMap<String,ArrayList<String>> currentusermp = new HashMap<>();
        currentusermp.put("Followinglist",Home.getUserData.followinglistuids);
        currentusermp.put("FollowersList",Home.getUserData.followerslistuids);

        getUserData.followStatusRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getData() == null){
                    Map<String,ArrayList<String>> mp  = new HashMap<>();
                    mp.put("FollowingList",getUserData.followinglistuids);
                    mp.put("FollowerList",getUserData.followerslistuids);
                    documentSnapshot.getReference().set(mp);
                }else
                    documentSnapshot.getReference().update("FollowerList",getUserData.followerslistuids);
            }
        });

       Home.getUserData.followStatusRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if(documentSnapshot.getData() == null){
                   Map<String,ArrayList<String>> mp  = new HashMap<>();
                   mp.put("FollowingList",Home.getUserData.followinglistuids);
                   mp.put("FollowerList",Home.getUserData.followerslistuids);
                   documentSnapshot.getReference().set(mp);
               }else
                    documentSnapshot.getReference().update("FollowingList",Home.getUserData.followinglistuids);
           }
       });

    }

    private void followUser() {
        Home.getUserData.followinglistuids.add(uid);
        followstatus = true;
        updateStatus();
        getUserData.snap.getReference().update("FollowersCount",followercountView.getText());
        Home.getUserData.snap.getReference().update("FollowingCount",String.valueOf(Integer.parseInt(Home.getUserData.followingcount) +1));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }




}