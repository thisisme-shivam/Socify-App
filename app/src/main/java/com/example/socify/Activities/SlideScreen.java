package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.socify.Adapters.SlideViewPagerAdapter;
import com.example.socify.databinding.ActivitySlideScreenBinding;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class SlideScreen extends AppCompatActivity {

    ActivitySlideScreenBinding binding;
    ViewPager viewPager;
    SlideViewPagerAdapter slideViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySlideScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewPager = binding.ViewPager;
        slideViewPagerAdapter = new SlideViewPagerAdapter(this);
        viewPager.setAdapter(slideViewPagerAdapter);

        SpringDotsIndicator springDotsIndicator = binding.dotsIndicator;

        SlideViewPagerAdapter slideViewPagerAdapter = new SlideViewPagerAdapter(this);
        viewPager.setAdapter(slideViewPagerAdapter);
        springDotsIndicator.setViewPager(viewPager);

        binding.Loginbtn.setOnClickListener(v -> startActivity(new Intent(SlideScreen.this, Login.class)));

        binding.Signupbtn.setOnClickListener(v -> startActivity(new Intent(SlideScreen.this,VerificationActivity.class)));


    }

}