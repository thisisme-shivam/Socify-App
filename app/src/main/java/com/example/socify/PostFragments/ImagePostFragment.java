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
import com.example.socify.ViewHolders.LoadUserPostsImages;
import com.example.socify.databinding.FragmentImagePostBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImagePostFragment extends Fragment {

    FragmentImagePostBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userimagesref;
    String uid;
    public ImagePostFragment(String uid){
        this.uid=uid;
        Log.i("college",Home.getUserData.college_name);

    }
    public ImagePostFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userimagesref = database.getReference("College").child(Home.getUserData.college_name).child("Posts").child(uid).child("All Images");

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseRecyclerOptions<PostMember> options =
                new FirebaseRecyclerOptions.Builder<PostMember>()
                        .setQuery(userimagesref, PostMember.class)
                        .build();

        FirebaseRecyclerAdapter<PostMember, LoadUserPostsImages> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PostMember, LoadUserPostsImages>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull LoadUserPostsImages holder, int position, @NonNull PostMember model) {
                        final String postkey = getRef(position).getKey();
                        holder.setPost(requireActivity(), model.getName(), model.getUrl(), model.getPostUri(), model.getTime(), model.getUid(), model.getType(), model.getDesc(), model.getUsername(), model.getPostid());
                    }

                    @NonNull
                    @Override
                    public LoadUserPostsImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.profile_postsimg,parent,false);
                        return new LoadUserPostsImages(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        binding.imgsRV.setAdapter(firebaseRecyclerAdapter);
        Log.e("Loading", "Yes");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImagePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}