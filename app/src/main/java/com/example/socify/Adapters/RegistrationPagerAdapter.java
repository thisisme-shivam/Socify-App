package com.example.socify.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socify.Fragement_registration.CoursesFragment;
import com.example.socify.Fragement_registration.GetCollegeFragment;
import com.example.socify.Fragement_registration.InterestsFragment;
import com.example.socify.Fragement_registration.NameFragement;
import com.example.socify.Fragement_registration.ProfilePic;


public class RegistrationPagerAdapter extends FragmentStateAdapter {


    public RegistrationPagerAdapter(FragmentActivity registration) {
        super(registration);
    }

    ProfilePic pic = new ProfilePic();
    NameFragement name = new NameFragement();
    InterestsFragment interests = new InterestsFragment();
    GetCollegeFragment getcollege = new GetCollegeFragment();
    CoursesFragment coursesFragment = new CoursesFragment();

    @NonNull
    @Override
    public Fragment createFragment(int position) {

       switch(position){
           case 0:
               return pic;
           case 1:
               return getcollege;
           case 2:
               return coursesFragment;
           case 3:
               return interests;
       }
       return pic;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
