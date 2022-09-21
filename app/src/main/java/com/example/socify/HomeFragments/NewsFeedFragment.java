package com.example.socify.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.AllChat;
import com.example.socify.Classes.PostMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.LoadNewsFeed;
import com.example.socify.databinding.FragmentNewsFeedBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsFeedFragment extends Fragment {

    FragmentNewsFeedBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference, likeref;
    Boolean likechecker = false;
    RecyclerView rec;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseRecyclerOptions<PostMember> options =
                new FirebaseRecyclerOptions.Builder<PostMember>()
                        .setQuery(reference, PostMember.class)
                        .build();

        FirebaseRecyclerAdapter<PostMember, LoadNewsFeed> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PostMember, LoadNewsFeed>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LoadNewsFeed holder, int position, @NonNull PostMember model) {
                        final String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final String postkey = getRef(position).getKey();

                        holder.setPost(requireActivity(), model.getName(), model.getUrl(), model.getPostUri(), model.getTime(), model.getUid(), model.getType(), model.getDesc(), model.getUsername());

                        holder.like.setOnClickListener(v -> {
                            likechecker = true;
                            likeref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if(likechecker.equals(true)) {
                                        if(snapshot.child(postkey).hasChild(currentUID)) {
                                            likeref.child(postkey).child(currentUID).removeValue();
                                            likechecker = false;
                                        }
                                    }
                                    else{
                                        likeref.child(postkey).child(currentUID).setValue(true);
                                        likechecker = false;
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        });

                    }

                    @NonNull
                    @Override
                    public LoadNewsFeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.newsfeedbox, parent, false);
                        return new LoadNewsFeed(view);
                    }


                };
        //Ordering data from bottom to top
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        firebaseRecyclerAdapter.startListening();
        
        rec = getView().findViewById(R.id.postsRV);
        rec.setLayoutManager(layoutManager);
        rec.setAdapter(firebaseRecyclerAdapter);

        binding.chat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AllChat.class);
            startActivity(intent);
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false);
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = database.getReference("Posts").child("All Posts");
        likeref = database.getReference("Likes");
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}