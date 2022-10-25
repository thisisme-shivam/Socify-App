package com.example.socify.QueryFragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.QuesitonLoadAdapter;
import com.example.socify.Classes.QuestionsMember;
import com.example.socify.ViewHolders.Load_Questions;
import com.example.socify.databinding.FragmentUserQueriesBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserQueriesFragment extends Fragment {

    FragmentUserQueriesBinding binding;
    DatabaseReference uidreference;
    FirebaseRecyclerAdapter<QuestionsMember, Load_Questions> firebaseRecyclerAdapter;
    ArrayList<QuestionsMember> questionsMembers;
    QuesitonLoadAdapter adapter;
    String tag;
    public UserQueriesFragment(String tag) {
        this.tag = tag;
    }

    public UserQueriesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionsMembers = new ArrayList<>();
        adapter = new QuesitonLoadAdapter(getActivity(),questionsMembers);
        uidreference = FirebaseDatabase.getInstance()
                .getReference()
                .child("College")
                .child(Home.getUserData.college_name)
                .child("Questions")
                .child(tag.replaceAll("[^A-Za-z]+", "").toLowerCase())
                .child(Home.getUserData.uid);

        uidreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuestionsMember question = dataSnapshot.getValue(QuestionsMember.class);
                    questionsMembers.add(question);
                }

                adapterStart();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void adapterStart() {

        adapter.userFragent = true;

        if(getContext()!=null)
            binding.userqueriesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.userqueriesRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserQueriesBinding.inflate(inflater, container, false);


        //Creating Adapter to Load the data in the RecyclerView
        return binding.getRoot();

    }
}