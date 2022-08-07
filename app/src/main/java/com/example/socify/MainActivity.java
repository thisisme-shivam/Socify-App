package com.example.socify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.cuberto.liquid_swipe.LiquidPager;
import com.example.socify.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    LiquidPager liquidPager;
    ViewPager viewPager;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        liquidPager = binding.pager;
        viewPager = new ViewPager(getSupportFragmentManager(), 1);
        liquidPager.setAdapter(viewPager);

       
    }


}