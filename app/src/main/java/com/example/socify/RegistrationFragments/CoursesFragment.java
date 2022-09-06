package com.example.socify.RegistrationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import com.example.socify.Activities.Registration;
import com.example.socify.Activities.SplashActivity;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Adapters.GetCourseAdapter;
import com.example.socify.Classes.Course;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.HelperClasses.OptimizedSearchCourses;
import com.example.socify.R;
import com.example.socify.databinding.FragmentCoursesBinding;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.jar.JarEntry;

public class CoursesFragment extends Fragment implements GetCollegeAdapter.CollegeViewHolder.Onitemclicked {

    FragmentCoursesBinding binding;
    SendProfileData sendProfileData = new SendProfileData();
    public GetCourseAdapter adapter;
    public ShimmerFrameLayout shimmerFrameLayout;
    public RecyclerView rec;
    SearchView searchView;
    OptimizedSearchCourses optimizedSearchCourses;
    public Handler hand = new Handler();
    CountDownTimer cntr;
    Integer waitingTime =200;
    public void setonclicklisteners() {

        binding.next3btn.setOnClickListener(v -> {
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
        optimizedSearchCourses = new OptimizedSearchCourses(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setonclicklisteners();
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(80);
        rec = view.findViewById(R.id.CoursesListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GetCourseAdapter(getContext(),Registration.courses,this::onclick);
        adapter.notifyDataSetChanged();
        rec.setAdapter(adapter);
        shimmerFrameLayout = getView().findViewById(R.id.shimmer_view_container);
        searchView = getView().findViewById(R.id.search_course);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(cntr != null){
                    cntr.cancel();
                }
                cntr = new CountDownTimer(waitingTime, 500) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        filter(newText);
                    }
                };
                cntr.start();
                return false;
            }
        });




    }

    public void filter(String s){
        optimizedSearchCourses.stopRunningThread();
        rec.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        optimizedSearchCourses.startSearch(s);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCoursesBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onclick(int position) {
        searchView.setQuery(optimizedSearchCourses.filterlist.get(position).getcoursename(),true);

        rec.setVisibility(View.GONE);
        Log.i("Course name is " , optimizedSearchCourses.filterlist.get(position).getcoursename());
    }


}