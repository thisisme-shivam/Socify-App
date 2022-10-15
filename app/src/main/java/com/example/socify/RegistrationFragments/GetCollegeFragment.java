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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Activities.Registration;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.HelperClasses.OptimizedSearchCollege;
import com.example.socify.R;
import com.example.socify.databinding.FragmentGetCollegeBinding;
import com.facebook.shimmer.ShimmerFrameLayout;


public class GetCollegeFragment extends Fragment implements GetCollegeAdapter.CollegeViewHolder.Onitemclicked {

    FragmentGetCollegeBinding binding;
    OptimizedSearchCollege optimizedSearch;
    public GetCollegeAdapter adapter;
    boolean notstopped  ;
    public RecyclerView rec;
    public ShimmerFrameLayout layout;
    public Handler hand = new Handler();
    SearchView searchview;
    CountDownTimer cntr;
    private Integer waitingTime = 200;
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

    }

    int i=1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentGetCollegeBinding.inflate(getLayoutInflater());
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(60);

        Log.i("made again", "uyes");
        rec = view.findViewById(R.id.CollegeListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GetCollegeAdapter(getContext(),Registration.colleges,this);
        rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        layout = getView().findViewById(R.id.shimmer_view_container);

        searchview = getView().findViewById(R.id.search_college);
        optimizedSearch = new OptimizedSearchCollege(this);
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
        Registration.fragment_curr_pos++;
        Registration.details.setCollege_name(optimizedSearch.newfilterlist.get(position).getCollege_name());
        SendProfileData data = new SendProfileData();
        searchview.setQuery(optimizedSearch.newfilterlist.get(position).getCollege_name(),true);
        data.sendCollegeName();
        getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, Registration.coursesFragment).commit();

    }



}