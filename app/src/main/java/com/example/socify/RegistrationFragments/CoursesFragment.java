package com.example.socify.RegistrationFragments;

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

public class CoursesFragment extends Fragment {

    FragmentCoursesBinding binding;
    SendProfileData sendProfileData = new SendProfileData();

    public void setonclicklisteners() {
        binding.next2btn.setOnClickListener(v -> {
            Registration.details.setCourse("CSE");
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