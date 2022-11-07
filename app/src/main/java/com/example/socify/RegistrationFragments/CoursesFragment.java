package com.example.socify.RegistrationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import com.example.socify.Activities.Registration;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Adapters.GetCourseAdapter;
import com.example.socify.HelperClasses.OptimizedCoursesSearch;
import com.example.socify.R;
import com.example.socify.databinding.FragmentCoursesBinding;
import com.facebook.shimmer.ShimmerFrameLayout;


public class CoursesFragment extends Fragment implements GetCollegeAdapter.CollegeViewHolder.Onitemclicked {

    FragmentCoursesBinding binding;
    public GetCourseAdapter adapter;
    public ShimmerFrameLayout shimmerFrameLayout;
    public RecyclerView rec;
    SearchView searchView;
    OptimizedCoursesSearch optimizedSearchCourses;
    public Handler hand = new Handler();
    CountDownTimer cntr;
    Integer waitingTime =200;
    boolean clickd = false;
    public Registration regActivity;
    InterestsFragment interestsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interestsFragment = new InterestsFragment();
        regActivity = (Registration) getActivity();
        optimizedSearchCourses = new OptimizedCoursesSearch(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController controller = Navigation.findNavController(view);


        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(80);
        rec = view.findViewById(R.id.CoursesListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GetCourseAdapter(getContext(),regActivity.courses,this::onclick);
        adapter.notifyDataSetChanged();
        rec.setAdapter(adapter);
        shimmerFrameLayout = getView().findViewById(R.id.shimmer_view_container);
        searchView = getView().findViewById(R.id.search_course);

        requireActivity().findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = CoursesFragmentDirections.actionCoursesFragmentToGetCollegeFragment();
                controller.navigate(action);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(clickd){
                    clickd = false;
                    return false;
                }
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
        NavController controller = Navigation.findNavController(getView());
        regActivity.profiledetails.put("Course",optimizedSearchCourses.newfilterlist.get(position).getcoursename());
        clickd = true;
        searchView.setQuery(optimizedSearchCourses.newfilterlist.get(position).getcoursename(),false);
        NavDirections action = CoursesFragmentDirections.actionCoursesFragmentToInterestsFragment();
        controller.navigate(action);
    }


}