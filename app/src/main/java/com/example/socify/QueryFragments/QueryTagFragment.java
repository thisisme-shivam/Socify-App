package com.example.socify.QueryFragments;

import static com.example.socify.Activities.Home.getUserData;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.UserTagsLoaderAdapter;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentQueryTagBinding;

import java.util.ArrayList;

public class QueryTagFragment extends Fragment {

    UserTagsLoaderAdapter adapter;
    RecyclerView recyclerView;
    public static ArrayList<String> tags;
    FragmentQueryTagBinding binding;
    NavController controller;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new UserTagsLoaderAdapter(getUserData.tags, getActivity());
        recyclerView = getActivity().findViewById(R.id.userTagsRV);
        recyclerView.setAdapter(adapter);
        controller = Navigation.findNavController(view);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Ask_QueryFragment.flag==true) {
//                    NavDirections directions = QueryTagFragmentDirections.actionQueryTagFragmentToNewsFeedFragment2();
//                    Ask_QueryFragment.flag=false;
//                    controller.navigate(directions);
//                }
//                else {
//                    NavDirections directions = QueryTagFragmentDirections.actionQueryTagFragmentToAccessMyFragment();
//                    controller.navigate(directions);
//                }
                NavDirections directions = QueryTagFragmentDirections.actionQueryTagFragmentToNewsFeedFragment2();
                Ask_QueryFragment.flag=false;
                controller.navigate(directions);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQueryTagBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}