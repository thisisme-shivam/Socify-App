package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Classes.QuestionsMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.Load_Questions;
import com.example.socify.databinding.FragmentAllQueriesBinding;
import com.example.socify.databinding.FragmentUserQueriesBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllQueriesFragment extends Fragment {

    FragmentAllQueriesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllQueriesBinding.inflate(inflater, container, false);


        //Creating Adapter to Load the data in the RecyclerView
        databaseReference = database.getReference("Questions").child("All Questions");
        FirebaseRecyclerOptions<QuestionsMember> options =
                new FirebaseRecyclerOptions.Builder<QuestionsMember>()
                        .setQuery(databaseReference,QuestionsMember.class)
                        .build();

        FirebaseRecyclerAdapter<QuestionsMember, Load_Questions> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionsMember, Load_Questions>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Load_Questions holder, int position, @NonNull QuestionsMember model) {
                        holder.setallitem(getActivity(), model.getName(),model.getUrl(),model.getUserid(),model.getKey(),model.getQuestion(),model.getTime(),model.getTag());
                    }

                    @NonNull
                    @Override
                    public Load_Questions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_all_query_layout,parent,false);
                        return new Load_Questions(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        binding.allqueriesRV.setAdapter(firebaseRecyclerAdapter);
        return binding.getRoot();

    }
}