package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.socify.ClubFragments.CreateClubFragment;
import com.example.socify.ClubFragments.MyClubFragment;
import com.example.socify.R;
import com.example.socify.databinding.ActivityClubsBinding;


public class clubs extends AppCompatActivity {

    ActivityClubsBinding binding;
    CreateClubFragment createClubFragment;
    MyClubFragment myClubFragment;
    public static int clubFragSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubsBinding.inflate(getLayoutInflater());
        createClubFragment = new CreateClubFragment();
        myClubFragment = new MyClubFragment();

        setContentView(binding.getRoot());

        if(clubFragSwitch == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.clubFragmentLoader, createClubFragment).commit();
        }
        else if(clubFragSwitch == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.clubFragmentLoader, myClubFragment).commit();
        }


    }
}