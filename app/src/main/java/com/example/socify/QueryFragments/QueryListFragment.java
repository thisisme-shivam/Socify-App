package com.example.socify.QueryFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.QueryTabViewPagerAdapter;
import com.example.socify.R;
import com.example.socify.databinding.FragmentQueryListBinding;

public class QueryListFragment extends Fragment {

    FragmentQueryListBinding binding;
    String tag ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QueryListFragmentArgs args = QueryListFragmentArgs.fromBundle(getArguments());
        tag = args.getTag();
        UserQueriesFragment userQueriesFragment = new UserQueriesFragment(tag);
        AllQueriesFragment allQueriesFragment = new AllQueriesFragment(tag);
        NavController controller = Navigation.findNavController(view);

        binding.tablayout.setupWithViewPager(binding.ViewPagerQuery);
        QueryTabViewPagerAdapter queryTabViewPagerAdapter = new QueryTabViewPagerAdapter(getChildFragmentManager(), 0);
        queryTabViewPagerAdapter.addfragment(allQueriesFragment, "All Queries");
        queryTabViewPagerAdapter.addfragment(userQueriesFragment, "My Queries");
        binding.ViewPagerQuery.setAdapter(queryTabViewPagerAdapter);

        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions = QueryListFragmentDirections.actionQueryListFragmentToQueryTagFragment();
                controller.navigate(directions);
            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQueryListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}