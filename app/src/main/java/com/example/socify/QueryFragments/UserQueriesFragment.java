package com.example.socify.QueryFragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.QnA;
import com.example.socify.Adapters.QuesitonLoadAdapter;
import com.example.socify.Classes.QuestionsMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.Load_Questions;
import com.example.socify.databinding.FragmentUserQueriesBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserQueriesFragment extends Fragment {

    FragmentUserQueriesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uidreference;
    ReplyFragment replyFragment;
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
        Context context  = getContext();
        if(context!=null)
            binding.userqueriesRV.setLayoutManager(new LinearLayoutManager(context));
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