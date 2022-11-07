package com.example.socify.RegistrationFragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Activities.Registration;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.HelperClasses.OptimizedCollegeSearch;
import com.example.socify.R;
import com.example.socify.databinding.FragmentGetCollegeBinding;
import com.facebook.shimmer.ShimmerFrameLayout;


public class GetCollegeFragment extends Fragment implements GetCollegeAdapter.CollegeViewHolder.Onitemclicked {

    FragmentGetCollegeBinding binding;
    OptimizedCollegeSearch optimizedSearch;
    public GetCollegeAdapter adapter;
    boolean notstopped  ;
    public RecyclerView rec;
    public ShimmerFrameLayout layout;
    public Handler hand = new Handler();
    SearchView searchview;
    public Registration regActivity;
    CoursesFragment coursesFragment;
    CountDownTimer cntr;
    private Integer waitingTime = 200;
    NavController controller;
    private void filter(String newText){

        optimizedSearch.stopRunningThread();
        rec.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        layout.startShimmer();
        optimizedSearch.startSearch(newText);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coursesFragment = new CoursesFragment();
        optimizedSearch = new OptimizedCollegeSearch(this);
        regActivity = (Registration) getActivity();
        adapter = new GetCollegeAdapter(requireContext(),regActivity.colleges,this);
        
    }

    int i=1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentGetCollegeBinding.inflate(getLayoutInflater());
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(60);
        controller = Navigation.findNavController(view);

        rec = view.findViewById(R.id.CollegeListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

        rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        layout = getView().findViewById(R.id.shimmer_view_container);

        searchview = getView().findViewById(R.id.search_college);

        requireActivity().findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = GetCollegeFragmentDirections.actionGetCollegeFragmentToCoursesFragment();
                controller.navigate(action);
            }
        });


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                return true;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetCollegeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onclick(int position) {
        controller = Navigation.findNavController(getView());
        String college = optimizedSearch.newfilterlist.get(position).getCollege_name();
        college = college.replaceAll("[^A-Za-z]+", "");
        Log.i("Name",college);
        regActivity.profiledetails.put("College",college);
        searchview.setQuery(optimizedSearch.newfilterlist.get(position).getCollege_name(),true);

        NavDirections action = GetCollegeFragmentDirections.actionGetCollegeFragmentToCoursesFragment();
        controller.navigate(action);

    }



}