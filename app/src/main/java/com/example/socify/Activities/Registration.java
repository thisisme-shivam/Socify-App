package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socify.Adapters.RegistrationPagerAdapter;
import com.example.socify.databinding.ActivityRegistrationBinding;

public class Registration extends AppCompatActivity {

    public static int a =10;
    ActivityRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RegistrationPagerAdapter adapter = new RegistrationPagerAdapter(this);
        binding.pagerRegistration.setAdapter(adapter);
    }
}