package com.example.socify.ClubFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Adapters.UserClubsViewPagerAdapter;
import com.example.socify.R;
import com.example.socify.databinding.FragmentOpenClubBinding;


public class OpenClubFragment extends Fragment {

    FragmentOpenClubBinding binding;


    public static OpenClubFragment newInstance(String param1, String param2) {
        OpenClubFragment fragment = new OpenClubFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClubChatFragment chatFragment = new ClubChatFragment();
        ClubProfileFragment profileFragment = new ClubProfileFragment();

        binding.clubsTabs.setupWithViewPager(binding.userClubsViewpager);
        UserClubsViewPagerAdapter clubsPageAdapter = new UserClubsViewPagerAdapter(getChildFragmentManager(), 0);
        clubsPageAdapter.addFragment(chatFragment, "Chat");
        clubsPageAdapter.addFragment(profileFragment, "Profile");

        binding.userClubsViewpager.setAdapter(clubsPageAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_open_club, container, false);
    }
}