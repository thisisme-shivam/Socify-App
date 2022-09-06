package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.R;
import com.example.socify.databinding.FragmentReplyBinding;

public class ReplyFragment extends Fragment {

    FragmentReplyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReplyBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}