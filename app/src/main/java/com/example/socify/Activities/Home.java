package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.example.socify.HomeFragments.DiscoverFragment;
import com.example.socify.HomeFragments.NewsFeedFragment;
import com.example.socify.R;
import com.example.socify.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    NewsFeedFragment newsFeedFragment;
    BottomNavigationView navigationView;
    int[] drawables;
    ClipData.Item itemSelected;

    int lastSelected;
    public void setIcon(int i){
        if(lastSelected == i ){
            return;
        }

        if(lastSelected > 2 )
            navigationView.getMenu().getItem(lastSelected).setIcon(drawables[lastSelected-1]);
        else
            navigationView.getMenu().getItem(lastSelected).setIcon(drawables[lastSelected]);
        if(i > 2 )
            navigationView.getMenu().getItem(i).setIcon(drawables[i+3]);
        else
            navigationView.getMenu().getItem(i).setIcon(drawables[i+4]);

    }

    public void itemselectedfromnavbar() {



        binding.bottomnavigationview.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.newsfeed) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
                setIcon(0);
                lastSelected = 0;
            }else if(item.getItemId() == R.id.discover){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, new DiscoverFragment()).commit();
                setIcon(1);
                lastSelected =1;
            }
            else if(item.getItemId() == R.id.addpost){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
            }
            else if(item.getItemId() == R.id.clubs){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
                setIcon(3);
                lastSelected = 3;
            }
            else if(item.getItemId() == R.id.profile){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
                setIcon(4);
                lastSelected = 4;
            }

            return false;
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigationView = binding.bottomnavigationview;
        drawables = new int[]{
                R.drawable.newsgrey,
                R.drawable.discovergrey,
                R.drawable.clubicongrey,
                R.drawable.profilegrey,
                R.drawable.newsicon,
                R.drawable.discovericon,
                R.drawable.clubicon,
                R.drawable.profileicon
        };
        binding.bottomnavigationview.setItemIconTintList(null);
        newsFeedFragment = new NewsFeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,newsFeedFragment).commit();
        navigationView.getMenu().getItem(0).setIcon(drawables[4]);
        lastSelected = 0;
        itemselectedfromnavbar();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}