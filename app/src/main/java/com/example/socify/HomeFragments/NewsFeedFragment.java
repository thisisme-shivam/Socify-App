package com.example.socify.HomeFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Activities.AllChat;
import com.example.socify.Activities.Home;
import com.example.socify.Adapters.GetNewsFeedAdapter;
import com.example.socify.Classes.PostMember;
import com.example.socify.InterfaceClass;
import com.example.socify.R;
import com.example.socify.databinding.FragmentNewsFeedBinding;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFeedFragment extends Fragment {

    FragmentNewsFeedBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference personPostsRef, likeref;
    GetNewsFeedAdapter getNewsFeed;
    ArrayList<PostMember> postMemberArrayList;
    RecyclerView rec;
    public static ArrayList<String> chattingusers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chattingusers = new ArrayList<>();
        postMemberArrayList = new ArrayList<>();
        getNewsFeed = new GetNewsFeedAdapter(getActivity(),postMemberArrayList);
        personPostsRef = FirebaseDatabase.getInstance().getReference().child("College")
                .child(Home.getUserData.college_name)
                .child("Posts");

        for(String personid:Home.getUserData.followinglistuids){
            personPostsRef.child(personid).child("All Images").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot post:snapshot.getChildren()){
                        PostMember member = post.getValue(PostMember.class);
                        postMemberArrayList.add(member);
                    }
                    getNewsFeed.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void setonclicklisteners() {

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllChat.class));
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNewsFeed.notifyDataSetChanged();
        //Ordering data from bottom to top
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rec = getView().findViewById(R.id.postsRV);
        rec.setLayoutManager(layoutManager);
        rec.setAdapter(getNewsFeed);
        setonclicklisteners();



        //Loading chatlist
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Chats")
                        .child(Home.getUserData.uid)
                        .get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                for (DataSnapshot userSnapshot : task.getResult().getChildren()) {
                                    chattingusers.add(userSnapshot.getKey());
                                    Log.e("UIDS", String.valueOf(chattingusers));
                                }
                            }
                        });
            }
        }).start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Online");
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Offline");
    }
}