package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socify.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}