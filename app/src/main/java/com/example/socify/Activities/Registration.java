package com.example.socify.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socify.Classes.College;
import com.example.socify.FireBaseClasses.UserDetails;
import com.example.socify.R;
import com.example.socify.RegistrationFragments.ProfilePic;
import com.example.socify.databinding.ActivityRegistrationBinding;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {

    ArrayList<College> colleges;

    ActivityRegistrationBinding binding;
    DatabaseReference ref;
    public static UserDetails details = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportFragmentManager().beginTransaction().add(R.id.frame_registration, new ProfilePic()).commit();

    }
}