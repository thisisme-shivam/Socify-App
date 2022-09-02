package com.example.socify.RegistrationFragments;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Activities.SplashActivity;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Classes.College;
import com.example.socify.R;
import com.example.socify.RegistrationFragments.CoursesFragment;
import com.example.socify.databinding.FragmentGetCollegeBinding;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class GetCollegeFragment extends Fragment implements GetCollegeAdapter.CollegeViewHolder.Onitemclicked {

    FragmentGetCollegeBinding binding;


    GetCollegeAdapter adapter;
    Thread th = new Thread();
    boolean notstopped  ;
    RecyclerView rec;
    ShimmerFrameLayout layout;
    Handler hand = new Handler();
    private void filter(String newText){
        rec.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        layout.startShimmer();

        ArrayList<College> filteredlist = new ArrayList<>();
        notstopped = false;
        th =new Thread(new Runnable() {
            @Override
            public void run() {
                for(College college : SplashActivity.colleges ){
                    if(notstopped)
                        break;

                    if(college.getCollege_name().toLowerCase().contains(newText.toLowerCase())){
                        Log.i("college name " , college.getCollege_name());
                        filteredlist.add(college);
                    }
                }


                hand.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Entering","fjdklsajf");

                        adapter.filterlist(filteredlist);
                        layout.stopShimmer();
                        layout.setVisibility(View.GONE);
                        rec.setVisibility(View.VISIBLE);

                    }
                });

            }
        });
        th.start();



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentGetCollegeBinding.inflate(getLayoutInflater());
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(60);

        rec = view.findViewById(R.id.CollegeListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GetCollegeAdapter(getContext(),SplashActivity.colleges,this);
        rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        layout = getView().findViewById(R.id.shimmer_view_container);

        SearchView seachview = getView().findViewById(R.id.search_college);

        seachview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notstopped = true;
                filter(newText);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetCollegeBinding.inflate(inflater, container, false);
        onclicklisteners();
        return binding.getRoot();
    }

    private void onclicklisteners() {
        binding.next3btn.setOnClickListener(v -> {
            CoursesFragment coursesFragment = new CoursesFragment();
            getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, coursesFragment).commit();
        });
    }

    @Override
    public void onclick(int position) {
        Log.i("COllege name is " ,SplashActivity.colleges.get(position).getCollege_name().toString());
    }


}