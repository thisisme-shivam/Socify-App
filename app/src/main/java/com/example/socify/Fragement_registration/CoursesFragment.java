package com.example.socify.Fragement_registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Registration;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentCoursesBinding;
import com.example.socify.databinding.FragmentGetCollegeBinding;
import com.example.socify.databinding.FragmentInterestsBinding;

public class CoursesFragment extends Fragment {

    FragmentCoursesBinding binding;
    public Registration registration;
    SendProfileData sendProfileData = new SendProfileData();

    public void setonclicklisteners() {
        binding.next2btn.setOnClickListener(v -> {
            registration.details.setCourse("CSE");
            //Sending Course
            sendProfileData.sendCourse();

            InterestsFragment interestsFragment = new InterestsFragment();
            getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, interestsFragment).commit();
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
        bar.setProgress(80);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        setonclicklisteners();
        return binding.getRoot();
    }
}