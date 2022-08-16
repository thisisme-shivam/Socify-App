package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.socify.R;
import com.example.socify.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomnavigationview.setItemIconTintList(null);
    }
}