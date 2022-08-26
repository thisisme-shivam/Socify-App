package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socify.R;
import com.example.socify.databinding.ActivityAllChatBinding;

public class AllChat extends AppCompatActivity {

    ActivityAllChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}