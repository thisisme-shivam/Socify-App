package com.example.socify.PostFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socify.Adapters.PostsViewPagerAdapter;
import com.example.socify.databinding.FragmentPostLoaderBinding;

public class PostLoaderFragment extends Fragment {

    String uid;
    FragmentPostLoaderBinding binding;

    ImagePostFragment imagePostFragment ;
    VideoPostFragment videoPostFragment ;
    public PostLoaderFragment(){

    }
    public PostLoaderFragment(String uid){
        this.uid=uid;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePostFragment = new ImagePostFragment(uid);
        videoPostFragment = new VideoPostFragment(uid);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagePostFragment = new ImagePostFragment(uid);
        videoPostFragment = new VideoPostFragment(uid);
        binding.tablayoutposts.setupWithViewPager(binding.ViewPagerposts);
        PostsViewPagerAdapter postsViewPagerAdapter = new PostsViewPagerAdapter(getChildFragmentManager(), 0);
        postsViewPagerAdapter.addfragment(imagePostFragment, "Images");
        postsViewPagerAdapter.addfragment(videoPostFragment, "Videos");
        binding.ViewPagerposts.setAdapter(postsViewPagerAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPostLoaderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}