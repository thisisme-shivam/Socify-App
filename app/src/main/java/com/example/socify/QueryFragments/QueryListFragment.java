package com.example.socify.QueryFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.QueryTabViewPagerAdapter;
import com.example.socify.databinding.FragmentQueryListBinding;

public class QueryListFragment extends Fragment {

    FragmentQueryListBinding binding;
    String tag ;
    public QueryListFragment(String tag) {
        this.tag = tag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

        UserQueriesFragment userQueriesFragment = new UserQueriesFragment(tag);
        AllQueriesFragment allQueriesFragment = new AllQueriesFragment(tag);

        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Home.class);
                startActivity(intent);
            }
        });

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