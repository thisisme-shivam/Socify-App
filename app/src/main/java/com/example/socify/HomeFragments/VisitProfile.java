package com.example.socify.HomeFragments;

import android.app.Notification;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.InterfaceClass;
import com.example.socify.PostFragments.PostLoaderFragment;
import com.example.socify.R;
import com.example.socify.SendNotification;
import com.example.socify.databinding.FragmentVisitProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class VisitProfile extends Fragment   {

    GetUserData getUserData;
    String uid ;
    boolean followstatus;
    FragmentVisitProfileBinding binding;
    NavController navController;
    PostLoaderFragment postLoaderFragment;

    AppCompatButton followButton;
    CircleImageView profilePhoto;
    MaterialTextView username,name;
    ChipGroup group;
    TextView followercountView,followingCountView;
    CountDownTimer timer;

    public VisitProfile(){

    }
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

        uid = VisitProfileArgs.fromBundle(getArguments()).getUid();
        postLoaderFragment = new PostLoaderFragment(uid);
        navController = Navigation.findNavController(view);

        binding.message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions = VisitProfileDirections.actionVisitProfileToChatRoomFragment2(getUserData.name, getUserData.imgurl, getUserData.uid);
                navController.navigate(directions);
            }
        });


        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions = VisitProfileDirections.actionVisitProfileToDiscoverFragment();
                navController.navigate(directions);
            }
        });


        getUserData = new GetUserData(uid, new InterfaceClass.VisitProfileInterface() {
            @Override
            public void onWorkDone() {
                if (getContext() != null) {
                    Glide.with(getContext()).load(getUserData.imgurl).placeholder(R.drawable.user).into(binding.profilePic);
                    binding.usernameProfile.setText("@" + getUserData.username);
                    binding.name.setText(getUserData.name);
                    binding.followerscount.setText(getUserData.followerscount);
                    binding.followingcount.setText(getUserData.followingcount);

                    for (String s : getUserData.tags) {
                        Chip chip = new Chip(requireActivity());
                        chip.setText(s);
                        chip.setElevation(5);
                        chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.black)));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.palette_light_teal)));
                        binding.chipGroup.addView(chip);

                    }

                    if(Home.getUserData.followinglistuids.contains(uid)){
                        binding.follow.setText("Following");
                        followstatus = true;
                    }else
                        followstatus = false;

                }
            }

            @Override
            public void onWorkNotDone() {

            }
        });

        setOnclickListeners();
    }

    private void setOnclickListeners() {


        binding.message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections  direction = VisitProfileDirections.actionVisitProfileToChatRoomFragment2(getUserData.name,getUserData.imgurl,getUserData.uid);
                navController.navigate(direction);
            }
        });


        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, new DiscoverFragment()).commit();
            }
        });

        binding.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(followstatus){
                    followstatus = false;
                    binding.follow.setText("Follow");
                    binding.followingcount.setText(String.valueOf(Integer.parseInt(binding.followingcount.getText().toString()) -1));
                }else{
                    followstatus = true;
                    binding.follow.setText("Following");
                    binding.followerscount.setText(String.valueOf(Integer.parseInt(binding.followerscount.getText().toString()) +1));
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
        Log.i("Ref",getUserData.followerslistuids.toString());
        mp.put("FollowerList",getUserData.followerslistuids);

        HashMap<String,ArrayList<String>> currentusermp = new HashMap<>();
        currentusermp.put("FollowingList",Home.getUserData.followinglistuids);

        getUserData.followerListRef.set(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("ghk","hkjlgfjkfhih");
                SendNotification.sendFollowNotification(requireContext(),getUserData.token,getUserData.uid);
            }
        });
        Home.getUserData.followingListRef.set(currentusermp);

    }

    private void followUser() {
        SendNotification.sendFollowNotification(getContext(),getUserData.token,getUserData.uid);
        Home.getUserData.followinglistuids.add(uid);
        getUserData.followerslistuids.add(Home.getUserData.uid);
        followstatus = true;
        updateStatus();
        getUserData.snap.getReference().update("FollowersCount",binding.followingcount.getText());
        Home.getUserData.snap.getReference().update("FollowingCount",String.valueOf(Integer.parseInt(Home.getUserData.followingcount) +1));

    }

    @Override
    public void onStart() {
        super.onStart();
        getChildFragmentManager().beginTransaction().replace(R.id.postloader,postLoaderFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }




}