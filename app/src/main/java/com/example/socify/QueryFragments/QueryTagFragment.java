package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.UserTagsLoaderAdapter;
import com.example.socify.R;
import com.example.socify.databinding.FragmentQueryTagBinding;

import java.util.ArrayList;

public class QueryTagFragment extends Fragment {

    UserTagsLoaderAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> tags = Home.getUserData.tags;
    FragmentQueryTagBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UserTagsLoaderAdapter(tags, getActivity());
        recyclerView = getActivity().findViewById(R.id.userTagsRV);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQueryTagBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }
}