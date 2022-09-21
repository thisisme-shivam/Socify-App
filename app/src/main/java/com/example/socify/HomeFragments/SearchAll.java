package com.example.socify.HomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Adapters.PersonAdapter;
import com.example.socify.Classes.Person;
import com.example.socify.HelperClasses.OptimizedSearchAll;
import com.example.socify.R;
import com.example.socify.databinding.FragmentSearchAllBinding;

import java.util.ArrayList;


public class SearchAll extends Fragment {

    FragmentSearchAllBinding binding;
    boolean first = true;
    RecyclerView rec;
    public PersonAdapter personAdapter;
    public ArrayList<Person> arrayList;
    SearchView searchView;
    OptimizedSearchAll searchALl;
    CountDownTimer cntr;
    public Handler hand = new Handler();
    private int  waitingTime = 200 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        personAdapter = new PersonAdapter(requireActivity(),arrayList);
        searchALl = new OptimizedSearchAll(this);
        Log.i("gbvjhfghj","vghfghf");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rec = getView().findViewById(R.id.personsrecyclerview);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        rec.setAdapter(personAdapter);
        first = false;
        binding.searchAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void filter(String newtext){
        searchALl.stopSearch();
        searchALl.startSearch(newtext);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchAllBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}