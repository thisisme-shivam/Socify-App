package com.example.socify.PostFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.Home;
import com.example.socify.Classes.PostMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.LoadUserPostsVideos;
import com.example.socify.databinding.FragmentVideoPostBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VideoPostFragment extends Fragment {

    FragmentVideoPostBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uservideosref;
    String uid;
    public VideoPostFragment(String uid) {
        Log.i("value",uid);
        this.uid = uid;
        Log.i("value",Home.getUserData.college_name);
        uservideosref = database.getReference("College").child(Home.getUserData.college_name).child("Posts").child(uid).child("All Videos");
    }

    public VideoPostFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FirebaseRecyclerOptions<PostMember> options =
                new FirebaseRecyclerOptions.Builder<PostMember>()
                        .setQuery(uservideosref, PostMember.class)
                        .build();

        FirebaseRecyclerAdapter<PostMember, LoadUserPostsVideos> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PostMember, LoadUserPostsVideos>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LoadUserPostsVideos holder, int position, @NonNull PostMember model) {
                        final String postkey = getRef(position).getKey();
                        holder.setPost(requireActivity(), model.getName(), model.getUrl(), model.getPostUri(), model.getTime(), model.getUid(), model.getType(), model.getDesc(), model.getUsername(), model.getPostid());
                    }

                    @NonNull
                    @Override
                    public LoadUserPostsVideos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.profile_postsvideo,parent,false);
                        return new LoadUserPostsVideos(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
//        GridLayoutManager glm = new GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false);
//        binding.videosRV.setLayoutManager(glm);
        binding.videosRV.setAdapter(firebaseRecyclerAdapter);
        Log.e("Loading", "Yes");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVideoPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}