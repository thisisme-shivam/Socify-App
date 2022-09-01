package com.example.socify.HomeFragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.Registration;
import com.example.socify.Fragement_registration.UserNameFragment;
import com.example.socify.R;
import com.example.socify.databinding.FragmentProfileBinding;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    public FragmentProfileBinding binding;
    public Home home;

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
                                EditUserDetails editUserDetails = new EditUserDetails();
                                getParentFragmentManager().beginTransaction().replace(R.id.FragmentView,editUserDetails).commit();
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

        home = (Home) getActivity();
        //Loading data into profile fragment
        if(home.imgurl!="No Image") {
            Picasso.get().load(Uri.parse(home.imgurl)).placeholder(R.drawable.user).into(binding.profilePic);
        }
        binding.branch.setText(home.branch);
        binding.passyear.setText(new StringBuilder().append("Batch: ").append(home.passyear).toString());
        binding.collegeName.setText(home.college_name);
        binding.name.setText(home.name);
        binding.usernameProfile.setText("@"+home.username);

        ArrayList<String> t = home.tags;

        //Adding Chips
        for(String s: t){
            Chip chip = new Chip(requireActivity());
            chip.setText(s);
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.palette_light_teal)));
            binding.chipGroup.addView(chip);
        }

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}