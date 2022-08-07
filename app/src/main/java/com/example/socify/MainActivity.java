package com.example.socify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cuberto.liquid_swipe.LiquidPager;
import com.example.socify.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    LiquidPager liquidPager;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        liquidPager = binding.pager;
        viewPager = new ViewPager(getSupportFragmentManager(), 1);
        liquidPager.setAdapter(viewPager);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}