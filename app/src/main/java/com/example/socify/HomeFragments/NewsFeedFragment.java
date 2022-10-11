package com.example.socify.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.AllChat;

import com.example.socify.Adapters.GetNewsFeedAdapter;
import com.example.socify.Classes.PostMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.LoadNewsFeed;
import com.example.socify.databinding.FragmentNewsFeedBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFeedFragment extends Fragment {

    FragmentNewsFeedBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference, likeref;
    Boolean likechecker = false;
    GetNewsFeedAdapter getNewsFeed;
    ArrayList<PostMember> postMemberArrayList;
    RecyclerView rec;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Ordering data from bottom to top
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        reference = database.getReference("Posts").child("All Posts");
        rec = getView().findViewById(R.id.postsRV);
        rec.setLayoutManager(layoutManager);
        postMemberArrayList = new ArrayList<>();
        getNewsFeed = new GetNewsFeedAdapter(getContext(),postMemberArrayList);
        rec.setAdapter(getNewsFeed);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postMemberArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if( !postMemberArrayList.contains(dataSnapshot.getValue(PostMember.class))) {
                        PostMember postMember = dataSnapshot.getValue(PostMember.class);
                        postMemberArrayList.add(postMember);
                    }
                }
                getNewsFeed.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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
        likeref = database.getReference("Likes");
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}