package com.example.socify.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socify.databinding.ActivityAllChatBinding;

public class AllChat extends AppCompatActivity {

    ActivityAllChatBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
