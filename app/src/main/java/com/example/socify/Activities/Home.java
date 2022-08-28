package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.socify.HomeFragments.DiscoverFragment;
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
    int[] drawables;

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

    public void createpopuponclicks() {


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
                showDialog();
            }
            else if(item.getItemId() == R.id.clubs){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
                setIcon(3);
                lastSelected = 3;
            }
            else if(item.getItemId() == R.id.profile){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, profileFragment).commit();
                setIcon(4);
                lastSelected = 4;
            }

            return false;
        });

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_popup);

        LinearLayout query = dialog.findViewById(R.id.querycreate);
        LinearLayout post = dialog.findViewById(R.id.postcreate);
        LinearLayout community = dialog.findViewById(R.id.communitycreate);

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for query creation to be written here
                Toast.makeText(Home.this, "Query selected", Toast.LENGTH_SHORT).show();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for post creation to be written here
                Toast.makeText(Home.this, "Post selected", Toast.LENGTH_SHORT).show();
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for community creation to be written here
                Toast.makeText(Home.this, "Community selected", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

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
        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,newsFeedFragment).commit();
        itemselectedfromnavbar();

        Dialog otpDialog = new Dialog(Home.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.post_creation_popup);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsFeedFragment = new NewsFeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,newsFeedFragment).commit();
        navigationView.getMenu().getItem(0).setIcon(drawables[4]);
        lastSelected = 0;
        itemselectedfromnavbar();

    }
}