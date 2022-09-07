package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class UserQueriesFragment extends Fragment {

    FragmentUserQueriesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;


    //Deleting User's Questions from the database when delete button is tapped
    public void delete(String time) {

        //Deleting from 'User's Questions' node
        databaseReference = database.getReference("Questions").child("User's Questions").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query = databaseReference.orderByChild("time").equalTo(time);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue();
                    Log.i("Question Deleted", "YES");
                    Toast.makeText(requireActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Deleting from 'All Questions' node
        databaseReference = database.getReference("Questions").child("All Questions");
        Query query1 = databaseReference.orderByChild("time").equalTo(time);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue();
                    Toast.makeText(requireActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserQueriesBinding.inflate(inflater, container, false);


        //Creating Adapter to Load the data in the RecyclerView
        databaseReference = database.getReference("Questions").child("User's Questions").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseRecyclerOptions<QuestionsMember> options =
                new FirebaseRecyclerOptions.Builder<QuestionsMember>()
                        .setQuery(databaseReference,QuestionsMember.class)
                        .build();


        FirebaseRecyclerAdapter<QuestionsMember, Load_Questions> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionsMember, Load_Questions>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Load_Questions holder, int position, @NonNull QuestionsMember model) {

                        holder.deleteitem(getActivity(), model.getName(),model.getUrl(),model.getUserid(),model.getKey(),model.getQuestion(),model.getTime(),model.getTag());
                        final String time = getItem(position).getTime();
                        holder.delbtn.setOnClickListener(v -> delete(time));
                    }

                    @NonNull
                    @Override
                    public Load_Questions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_user_query_layout,parent,false);
                        return new Load_Questions(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        binding.userqueriesRV.setAdapter(firebaseRecyclerAdapter);
        return binding.getRoot();

    }
}