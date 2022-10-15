package com.example.socify.HomeFragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.InterfaceClass;
import com.example.socify.R;
import com.example.socify.databinding.FragmentVisitProfileBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

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
    public VisitProfile(String uid, boolean followstatus) {
        Log.i("person uid", uid);
        this.uid  = uid;
        this.followstatus = followstatus;
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


        getUserData = new GetUserData(uid, new InterfaceClass.LoadDataInterface() {
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

                    if (followstatus)
                        followButton.setText("Following");

                    if (getUserData.profilestatus.equals("private")) {
                        getView().findViewById(R.id.privatemessage).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        setOnclickListeners();
    }

    private void setOnclickListeners() {
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!followstatus)
                    followUser();
                else
                    unfollowuser();
            }
        });
    }

    private void unfollowuser() {
        followButton.setText("Follow");
        Home.getUserData.followinglistuids.remove(uid);
        getUserData.followerslistuids.remove(Home.getUserData.uid);
        followstatus = false;
        followercountView.setText(String.valueOf(Integer.parseInt(followercountView.getText().toString()) -1));

        Home.getUserData.followingcount = String.valueOf(Integer.parseInt(Home.getUserData.followingcount )- 1);
        getUserData.followerscount = String.valueOf(Integer.parseInt(getUserData.followerscount )- 1);

        updateStatus();
    }

    private void updateStatus(){
        HashMap<String,ArrayList<String>> mp = new HashMap<>();
        mp.put("Followinglist",getUserData.followinglistuids);
        mp.put("FollowersList",getUserData.followerslistuids);

        HashMap<String,ArrayList<String>> currentusermp = new HashMap<>();
        currentusermp.put("Followinglist",Home.getUserData.followinglistuids);
        currentusermp.put("FollowersList",Home.getUserData.followerslistuids);

        getUserData.followStatusRef.set(mp);
        Home.getUserData.followStatusRef.set(currentusermp);

        updatefirebase();


    }

    private void followUser() {
        followButton.setText("Following");
        Home.getUserData.followinglistuids.add(uid);
        getUserData.followerslistuids.add(Home.getUserData.uid);
        followstatus = true;

        followercountView.setText(String.valueOf(Integer.parseInt(getUserData.followerscount)+1));

        Home.getUserData.followingcount = String.valueOf(Integer.parseInt(Home.getUserData.followingcount )+1);
        getUserData.followerscount = String.valueOf(Integer.parseInt(getUserData.followerscount ) + 1);
        updateStatus();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    private void updatefirebase(){

        Log.i("valus is " , getUserData.followerscount);
        getUserData.snap.getReference().update("FollowersCount", getUserData.followerscount);
        Home.getUserData.snap.getReference().update("FollowingCount",Home.getUserData.followingcount);
    }



}