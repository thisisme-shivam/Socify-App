package com.example.socify.Fragement_registration;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.R;
import com.example.socify.databinding.FragmentGetCollegeBinding;
import com.example.socify.databinding.FragmentProfilePicBinding;

import java.util.Objects;


public class GetCollegeFragment extends Fragment {

    FragmentGetCollegeBinding binding;

    public void setonclicklisteners() {
        binding.next3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoursesFragment coursesFragment = new CoursesFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_registration, coursesFragment).commit();
            }
        });
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(60);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentGetCollegeBinding.inflate(inflater, container, false);
        setonclicklisteners();
        return binding.getRoot();
    }
}