package com.example.socify.QueryFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.QuesitonLoadAdapter;
import com.example.socify.Classes.QuestionsMember;
import com.example.socify.databinding.FragmentAllQueriesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class AllQueriesFragment extends Fragment {

    FragmentAllQueriesBinding binding;
    DatabaseReference uidreference;
    QuesitonLoadAdapter adapter;
    ArrayList<String> uidList;
    ArrayList<QuestionsMember> questions;
    String tag ;
    int total = 0;
    HashSet<String> questionsMemberHashSet;

    public AllQueriesFragment(String tag) {
        this.tag = tag.replaceAll("[^A-Za-z]+", "").toLowerCase();
    }

    public AllQueriesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uidList = new ArrayList<>();
        questions = new ArrayList<>();
        questionsMemberHashSet = new HashSet<>();
        uidreference = FirebaseDatabase.getInstance().getReference().child("College").child(Home.getUserData.college_name).child("Questions").child(tag);
        Log.i("value",tag);
        uidreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total = (int) snapshot.getChildrenCount();
                for (DataSnapshot userid: snapshot.getChildren()){
                    total--;
                    if( !userid.getKey().equals(Home.getUserData.uid) ) {
                        Log.i("value",userid.getKey());

                        uidList.add(userid.getKey());
                        fetchDataofUser(userid.getKey());
                    }
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void adapterStart() {
        adapter = new QuesitonLoadAdapter(getActivity(),questions);

        Context context = getContext();
        if(context!=null)
            binding.allqueriesRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.allqueriesRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        for(String uid:uidList) {
            //Creating Adapter to Load the data in the RecyclerView


    }

    private void fetchDataofUser(String key) {

         FirebaseDatabase.getInstance()
                .getReference()
                .child("College")
                .child(Home.getUserData.college_name)
                .child("Questions")
                .child(tag)
                .child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    QuestionsMember question = new QuestionsMember();

                                    question.setQuestion(String.valueOf(dataSnapshot.child("question").getValue()));
                                    question.setUsername(String.valueOf(dataSnapshot.child("username").getValue()));
                                    question.setKey(String.valueOf(dataSnapshot.child("key").getValue()));
                                    question.setTag(String.valueOf(dataSnapshot.child("tag").getValue()));
                                    question.setTime(String.valueOf(dataSnapshot.child("time").getValue()));
                                    question.setQuestionURI(String.valueOf(dataSnapshot.child("questionURI").getValue()));
                                    question.setUserid(String.valueOf(dataSnapshot.child("userid").getValue()));
                                    Log.i("question",question.getQuestion());


                                    if(!questionsMemberHashSet.contains(question.getKey())){
                                        questionsMemberHashSet.add(question.getKey());
                                        questions.add(question);
                                    }

                                }
                                if(total == 0){
                                    Log.i("value", String.valueOf(total));
                                    adapterStart();
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllQueriesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}