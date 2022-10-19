package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.socify.R;
import com.example.socify.databinding.ActivityFullScreenImgBinding;

public class FullScreenImg extends AppCompatActivity {

    ActivityFullScreenImgBinding binding;
    String imgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenImgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imgurl = getIntent().getStringExtra("imgurl");

        Glide.with(this).load(imgurl).into(binding.zoomedimg);

    }
}