package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.socify.Adapters.SlideViewPagerAdapter;
import com.example.socify.R;
import com.example.socify.databinding.ActivitySlideScreenBinding;

public class SlideScreen extends AppCompatActivity {

    ActivitySlideScreenBinding binding;
    SlideViewPagerAdapter slideViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySlideScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        slideViewPagerAdapter = new SlideViewPagerAdapter(this);
        binding.ViewPager.setAdapter(slideViewPagerAdapter);



        SlideViewPagerAdapter slideViewPagerAdapter = new SlideViewPagerAdapter(this);
        binding.ViewPager.setAdapter(slideViewPagerAdapter);
        binding.dotsIndicator.setViewPager(binding.ViewPager);

        binding.Loginbtn.setOnClickListener(v ->
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SlideScreen.this, Login.class));
                    finish();
                }
            }, 400);


        } );

        binding.Signupbtn.setOnClickListener(v -> {
            startActivity(new Intent(SlideScreen.this,VerificationActivity.class));
            finish();
        });


    }

    public static class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    }
}