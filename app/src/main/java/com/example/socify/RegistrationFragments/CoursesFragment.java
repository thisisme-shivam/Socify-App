package com.example.socify.RegistrationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Registration;
import com.example.socify.Activities.SplashActivity;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Adapters.GetCourseAdapter;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentCoursesBinding;

import java.util.ArrayList;

public class CoursesFragment extends Fragment implements GetCollegeAdapter.CollegeViewHolder.Onitemclicked {

    FragmentCoursesBinding binding;
    public Registration registration;
    SendProfileData sendProfileData = new SendProfileData();
    GetCourseAdapter adapter;

    RecyclerView rec;
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
        rec = view.findViewById(R.id.CoursesListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GetCourseAdapter(getContext(),Registration.courses,this::onclick);
        adapter.notifyDataSetChanged();
        rec.setAdapter(adapter);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        setonclicklisteners();
        return binding.getRoot();
    }

    @Override
    public void onclick(int position) {
        Log.i("Course name is " , Registration.courses.get(position).getcoursename());
    }
}