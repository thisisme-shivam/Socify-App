package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.Classes.AnswerMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.Load_Answers;
import com.example.socify.databinding.FragmentReplyBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReplyFragment extends Fragment {

    FragmentReplyBinding binding;
    String uid, question, postkey, name, tag, queryUrl,time;
    AnswerMember member = new AnswerMember();
    DatabaseReference questionref;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        ReplyFragmentArgs args = ReplyFragmentArgs.fromBundle(getArguments());
        uid = args.getUid();
        question = args.getQuestion();
        postkey = args.getPostkey();
        name = args.getUsername();
        tag = args.getTag();
        queryUrl = args.getQuestionURI();
        time = args.getTime();

        super.onCreate(savedInstanceState);
        questionref = FirebaseDatabase.getInstance()
                .getReference("College")
                .child(Home.getUserData.college_name)
                .child("Questions")
                .child(tag.replaceAll("[^A-Za-z]+", "").toLowerCase())
                .child(uid)
                .child(postkey)
                .child("Answers");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.question.setText(question);
        binding.username.setText("Posted by " +  name);
        binding.tag.setText(tag);
        binding.timestamp.setText(time);
        if(!queryUrl.equals("No Image")) {
            Glide.with(this).load(queryUrl).into(binding.questionimg);
            binding.questionimg.setVisibility(View.VISIBLE);
        }
        binding.replylayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAnswer();
            }
        });

        //Fetching the replies from database into the recyclerview
        FirebaseRecyclerOptions<AnswerMember> options =
                new FirebaseRecyclerOptions.Builder<AnswerMember>()
                        .setQuery(questionref, AnswerMember.class)
                        .build();

        FirebaseRecyclerAdapter<AnswerMember, Load_Answers> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<AnswerMember, Load_Answers>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Load_Answers holder, int position, @NonNull AnswerMember model) {
                        holder.setAnswer(requireActivity().getApplication(), model.getName(), model.getUid(), model.getAnswer(), model.getTime());
                    }

                    @NonNull
                    @Override
                    public Load_Answers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.reply_layout,parent,false);
                        return new Load_Answers(view);
                    }
                };

        //Ordering data from bottom to top
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        firebaseRecyclerAdapter.startListening();
        binding.repliesRV.setLayoutManager(layoutManager);
        binding.repliesRV.setAdapter(firebaseRecyclerAdapter);


    }

    private void postAnswer() {
        String answer = binding.replytxt.getText().toString().trim();
        if(answer.equals("")) {
            Toast.makeText(getContext(), "Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
        final String saveDate = currenDate.format(cdate.getTime());
        member.setAnswer(answer);
        member.setTime(saveDate);
        member.setUid(Home.getUserData.uid);
        member.setName(Home.getUserData.username);
        String id = questionref.push().getKey();
        questionref.child(id).setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                binding.replytxt.setText("");
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReplyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }




}