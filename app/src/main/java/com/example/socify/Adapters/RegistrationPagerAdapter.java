package com.example.socify.Adapters;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socify.Fragement_registration.InterestsFragment;
import com.example.socify.Fragement_registration.NameFragement;
import com.example.socify.Fragement_registration.ProfilePic;
import com.example.socify.MainActivity;
import com.example.socify.Onboarding_Fragments.BuddyFragment;
import com.example.socify.databinding.ActivityMainBinding;

import java.util.jar.Attributes;

public class RegistrationPagerAdapter extends FragmentStateAdapter {


    public RegistrationPagerAdapter(FragmentActivity registration) {
        super(registration);
    }

    ProfilePic pic = new ProfilePic();
    NameFragement name = new NameFragement();
    InterestsFragment interests = new InterestsFragment();

    @NonNull
    @Override
    public Fragment createFragment(int position) {

       switch(position){
           case 0:
               return pic;
           case 1:
               return name;
           case 2:
               return interests;
       }
       return pic;
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
