package com.example.socify.HomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Adapters.RecyclerClubAdapter;
import com.example.socify.Adapters.RecyclerCommunityAdapter;
import com.example.socify.Models.DiscoverClubModel;
import com.example.socify.Models.DiscoverCommunityModel;
import com.example.socify.R;
import com.example.socify.databinding.FragmentDiscoverBinding;

import java.util.ArrayList;


public class DiscoverFragment extends Fragment {

    FragmentDiscoverBinding binding;
    ArrayList<DiscoverClubModel> arrClubs = new ArrayList<>();
    ArrayList<DiscoverCommunityModel> arrCommunities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);

        LinearLayoutManager clubLinearLayoutManager = new LinearLayoutManager(getContext());
        clubLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.clubsRecyclerview.setLayoutManager(clubLinearLayoutManager);
        arrClubs.add(new DiscoverClubModel(R.drawable.club_img, "Club 1"));
        arrClubs.add(new DiscoverClubModel(R.drawable.club_img, "Club 2"));
        arrClubs.add(new DiscoverClubModel(R.drawable.club_img, "Club 3"));
        arrClubs.add(new DiscoverClubModel(R.drawable.club_img, "Club 4"));
        arrClubs.add(new DiscoverClubModel(R.drawable.club_img, "Club 5"));

        RecyclerClubAdapter clubAdapter = new RecyclerClubAdapter(this.getContext(), arrClubs);
        binding.clubsRecyclerview.setAdapter(clubAdapter);

        LinearLayoutManager communityLinearLayoutManager = new LinearLayoutManager(getContext());
        communityLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.communityRecyclerView.setLayoutManager(communityLinearLayoutManager);
        arrCommunities.add(new DiscoverCommunityModel(R.drawable.community_img, "Community 1"));
        arrCommunities.add(new DiscoverCommunityModel(R.drawable.community_img, "Community 2"));
        arrCommunities.add(new DiscoverCommunityModel(R.drawable.community_img, "Community 3"));
        arrCommunities.add(new DiscoverCommunityModel(R.drawable.community_img, "Community 4"));
        arrCommunities.add(new DiscoverCommunityModel(R.drawable.community_img, "Community 5"));

        RecyclerCommunityAdapter communityAdapter = new RecyclerCommunityAdapter(this.getContext(), arrCommunities);
        binding.communityRecyclerView.setAdapter(communityAdapter);

        return binding.getRoot();
    }
}