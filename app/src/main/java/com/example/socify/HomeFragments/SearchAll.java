package com.example.socify.HomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Adapters.PersonAdapter;
import com.example.socify.Classes.Person;
import com.example.socify.R;
import com.example.socify.databinding.FragmentSearchAllBinding;

import java.util.ArrayList;


public class SearchAll extends Fragment {

    FragmentSearchAllBinding binding;
    boolean first = true;
    RecyclerView rec;
    PersonAdapter personAdapter;
    ArrayList<Person> arrayList;
    SearchView searchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        personAdapter = new PersonAdapter(requireContext(),arrayList);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(first){
            rec = getView().findViewById(R.id.personsrecyclerview);
            rec.setHasFixedSize(true);
            rec.setLayoutManager(new LinearLayoutManager(getContext()));
            rec.setAdapter(personAdapter);
            first = false;
            searchView = binding.searchCollege;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchAllBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}