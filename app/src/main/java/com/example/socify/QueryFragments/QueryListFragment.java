package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Adapters.QueryTabViewPagerAdapter;
import com.example.socify.Classes.QuestionsMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.Load_Questions;
import com.example.socify.databinding.FragmentQueryListBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QueryListFragment extends Fragment {

    FragmentQueryListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

        UserQueriesFragment userQueriesFragment = new UserQueriesFragment();
        AllQueriesFragment allQueriesFragment = new AllQueriesFragment();

        binding.tablayout.setupWithViewPager(binding.ViewPagerQuery);
        QueryTabViewPagerAdapter queryTabViewPagerAdapter = new QueryTabViewPagerAdapter(getChildFragmentManager(), 0);
        queryTabViewPagerAdapter.addfragment(allQueriesFragment, "All Queries");
        queryTabViewPagerAdapter.addfragment(userQueriesFragment, "My Queries");
        binding.ViewPagerQuery.setAdapter(queryTabViewPagerAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQueryListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}