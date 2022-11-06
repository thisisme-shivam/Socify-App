package com.example.socify.HomeFragments;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.PostFragments.PostLoaderFragment;
import com.example.socify.R;
import com.example.socify.databinding.FragmentProfileBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    public FragmentProfileBinding binding;
    public Home home;
    PostLoaderFragment  postLoaderFragment = new PostLoaderFragment(Home.getUserData.uid);
    NavController controller;

    public void setonclicklisteners() {

        binding.threeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_details:
                                //Switching to new fragment
                                NavDirections action = ProfileFragmentDirections.actionProfileFragmentToEditUserDetails();
                                controller.navigate(action);
                                return true;
                            case R.id.privacy:
                                Toast.makeText(requireActivity(), "Privacy", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.settings:
                                Toast.makeText(requireActivity(), "Settings", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.threedotsprofile);
                popupMenu.show();
            }
        });
    }

    private void setUserData() {

        //putting data on views of profile fragment
        if(Home.getUserData.imgurl!=null) {
            Glide.with(this).load(Uri.parse(Home.getUserData.imgurl)).placeholder(R.drawable.user).into(binding.profilePic);
        }
        binding.passyear.setText(Home.getUserData.passyear);
        binding.name.setText(Home.getUserData.name);
        binding.usernameProfile.setText("@" + Home.getUserData.username);
        binding.followerscount.setText(Home.getUserData.followerscount);
        binding.followingcount.setText(Home.getUserData.followingcount);

        ArrayList<String> t = Home.getUserData.tags;

        //Adding Chips
        for(String s: t){
            Chip chip = new Chip(requireActivity());
            chip.setText(s);
            chip.setElevation(5);
            chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.black)));
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.palette_light_teal)));
            binding.chipGroup.addView(chip);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container,false);

        setUserData();
        setonclicklisteners();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        getChildFragmentManager().beginTransaction().replace(R.id.postloader,postLoaderFragment).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottomnavigationview).setVisibility(View.VISIBLE);
    }
}