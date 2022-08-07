package com.example.socify;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.socify.OnBoardingFragments.BuddyFragment;
import com.example.socify.OnBoardingFragments.CommunityFragment;
import com.example.socify.OnBoardingFragments.EventsFragment;
import com.example.socify.OnBoardingFragments.QuestionsFragment;

public class ViewPager extends FragmentPagerAdapter {
    public ViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new BuddyFragment();
            case 1: return new CommunityFragment();
            case 2: return new EventsFragment();
            case 3: return new QuestionsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
