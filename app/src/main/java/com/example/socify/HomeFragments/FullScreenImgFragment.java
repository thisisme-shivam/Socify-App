package com.example.socify.HomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.socify.R;
import com.example.socify.databinding.FragmentFullScreenImgBinding;


public class FullScreenImgFragment extends Fragment {

    FragmentFullScreenImgBinding binding;
    String imgurl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgurl = getArguments().getString("imgurl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFullScreenImgBinding.inflate(inflater, container, false);

        Glide.with(this).load(imgurl).into(binding.zoomedimg);

        return binding.getRoot();
    }
}