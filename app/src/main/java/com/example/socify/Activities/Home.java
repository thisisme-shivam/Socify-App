package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.socify.HomeFragments.NewsFeedFragment;
import com.example.socify.HomeFragments.ProfileFragment;
import com.example.socify.R;
import com.example.socify.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    NewsFeedFragment newsFeedFragment;
    ProfileFragment profileFragment;
    BottomNavigationView navigationView;


    public void itemselectedfromnavbar() {
        navigationView = binding.bottomnavigationview;

        binding.bottomnavigationview.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.newsfeed) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
                navigationView.getMenu().getItem(0).setChecked(true);
            }
            return false;
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomnavigationview.setItemIconTintList(null);
        newsFeedFragment = new NewsFeedFragment();
        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,profileFragment).commit();
        itemselectedfromnavbar();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}