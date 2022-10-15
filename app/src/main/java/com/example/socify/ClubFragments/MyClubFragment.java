package com.example.socify.ClubFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.QueryTabViewPagerAdapter;
import com.example.socify.Adapters.UserClubsRecyclerAdapter;
import com.example.socify.Adapters.UserClubsViewPagerAdapter;
import com.example.socify.Models.UserClubsModel;
import com.example.socify.R;
import com.example.socify.databinding.FragmentMyClubBinding;

import java.util.ArrayList;

public class MyClubFragment extends Fragment{

    FragmentMyClubBinding binding;
    ArrayList<UserClubsModel> arrUserClubs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyClubBinding.inflate(inflater, container, false);

        LinearLayoutManager userClubManager = new LinearLayoutManager(getContext());
        userClubManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.userClubsRecyclerview.setLayoutManager(userClubManager);
        arrUserClubs.add(new UserClubsModel(R.drawable.dance, "Dance Club", "Hello", "today"));
        arrUserClubs.add(new UserClubsModel(R.drawable.club_icon, "Nobody Club", "Hey There", "yesterday"));
        arrUserClubs.add(new UserClubsModel(R.drawable.music, "Music Club", "Now you see me!", "9/11/22"));
        arrUserClubs.add(new UserClubsModel(R.drawable.dorime, "dorime Club", "ha ha ha", "9/11/22"));
        arrUserClubs.add(new UserClubsModel(R.drawable.club_icon, "coding Club", "Chala jaa ****", "10/08/22"));
        arrUserClubs.add(new UserClubsModel(R.drawable.doge, "fitness Club", "Hue hue hue", "9/09/22"));
        arrUserClubs.add(new UserClubsModel(R.drawable.food, "food Club", "food food food food", "12/10/22"));
        arrUserClubs.add(new UserClubsModel(R.drawable.hatlist, "hatlist Club", "hat hat hat", "23/01/21"));
        arrUserClubs.add(new UserClubsModel(R.drawable.aptitude, "aptitude Club", "shubham shrivastava", "23/01/21"));
        arrUserClubs.add(new UserClubsModel(R.drawable.events, "events Club", "event event event", "23/01/21"));

        UserClubsRecyclerAdapter userClubsAdapter = new UserClubsRecyclerAdapter(this.getContext(), arrUserClubs);
        binding.userClubsRecyclerview.setAdapter(userClubsAdapter);
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Home.class));
            }
        });

        return binding.getRoot();
    }


}