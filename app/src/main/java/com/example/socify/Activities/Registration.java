package com.example.socify.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socify.FireBaseClasses.UserDetails;
import com.example.socify.Fragement_registration.ProfilePic;
import com.example.socify.R;
import com.example.socify.databinding.ActivityRegistrationBinding;

public class Registration extends AppCompatActivity {

    public static int a =10;
    ActivityRegistrationBinding binding;
    public static UserDetails details = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().add(R.id.frame_registration, new ProfilePic()).commit();

    }
}