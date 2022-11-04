package com.example.socify.HomeFragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.InterfaceClass;
import com.example.socify.PostFragments.PostLoaderFragment;
import com.example.socify.R;
import com.example.socify.databinding.FragmentVisitProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VisitProfile extends Fragment   {

    GetUserData getUserData;
    String uid ;
    boolean followstatus;
    FragmentVisitProfileBinding binding;
    NavController navController;
    PostLoaderFragment postLoaderFragment = new PostLoaderFragment();

    CountDownTimer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uid = VisitProfileArgs.fromBundle(getArguments()).getUid();

        navController = Navigation.findNavController(view);

        binding.message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions = VisitProfileDirections.actionVisitProfileToChatRoomFragment2(getUserData.name, getUserData.imgurl, getUserData.uid);
                navController.navigate(directions);
            }
        });


        binding.backbtnvisit.setOnClickListener(new View.OnClickListener() {
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
                    Glide.with(getContext()).load(getUserData.imgurl).placeholder(R.drawable.user).into(binding.profilePicVisit);
                    binding.usernameProfileVisit.setText("@" + getUserData.username);
                    binding.namevisit.setText(getUserData.name);
                    binding.followerscountvisit.setText(getUserData.followerscount);
                    binding.followingcountvisit.setText(getUserData.followingcount);

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

                    if (getUserData.profilestatus.equals("private")) {
//                        getView().findViewById(R.id.privatemessage).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        setOnclickListeners();
    }

    private void setOnclickListeners() {

        binding.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(followstatus){
                    followstatus = false;
                    binding.follow.setText("Follow");
                    binding.followerscountvisit.setText(String.valueOf(Integer.parseInt(binding.followerscountvisit.getText().toString()) -1));
                }else{
                    followstatus = true;
                    binding.follow.setText("Following");
                    binding.followerscountvisit.setText(String.valueOf(Integer.parseInt(binding.followerscountvisit.getText().toString()) +1));
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
        binding.follow.setText("Follow");
        Home.getUserData.followinglistuids.remove(uid);
        getUserData.followerslistuids.remove(Home.getUserData.uid);
        followstatus = false;

        getUserData.snap.getReference().update("FollowersCount",binding.followerscountvisit.getText());
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
        getUserData.snap.getReference().update("FollowersCount",binding.followerscountvisit.getText());
        Home.getUserData.snap.getReference().update("FollowingCount",String.valueOf(Integer.parseInt(Home.getUserData.followingcount) +1));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitProfileBinding.inflate(getLayoutInflater());
        getChildFragmentManager().beginTransaction().replace(R.id.postloader,postLoaderFragment).commit();
        return binding.getRoot();
    }




}