package com.example.socify.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.AllChat;
import com.example.socify.R;
import com.example.socify.databinding.FragmentNewsFeedBinding;

public class NewsFeedFragment extends Fragment {

    FragmentNewsFeedBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.chat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AllChat.class);

            startActivity(intent);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentNewsFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}