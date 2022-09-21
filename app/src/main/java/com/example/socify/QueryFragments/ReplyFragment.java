package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.Classes.AnswerMember;
import com.example.socify.R;
import com.example.socify.ViewHolders.Load_Answers;
import com.example.socify.databinding.FragmentReplyBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReplyFragment extends Fragment {

    FragmentReplyBinding binding;
    String uid, question, postkey, name, tag, replier_name;
    DocumentReference reference, reference2;
    DatabaseReference databaseReferenceall, databaseReferenceuser;
    AnswerMember member = new AnswerMember();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference approvals, allquestions;
    //Flag to use for approval checking
    Boolean approvalchecker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = this.getArguments();

        if(extra!=null) {
            uid = extra.getString("uid");
            question = extra.getString("question");
            postkey = extra.getString("postkey");
            name = extra.getString("name");
            tag = extra.getString("tag");

            System.out.println(uid);
            System.out.println(question);
            System.out.println(tag);
            System.out.println(postkey);
            System.out.println(name);
        }
        else{
            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
        }

        reference = db.collection("Profiles").document(uid);
        reference2 = db.collection("Profiles").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void onStart() {
        super.onStart();

        //Asker's Details
        reference.get()
                .addOnCompleteListener(task -> {
                    if(task.getResult().exists()) {
                        String name = task.getResult().getString("Name");
                        binding.question.setText(question);
                        binding.username.setText("Posted by " +  name);
                        binding.tag.setText(tag);
                    }
                });

        //Replier's Details
        reference2.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            replier_name = task.getResult().getString("Name");
                        }
                    }
                });


        //Storing Replies into the database
        binding.replylayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.replytxt.equals("")) {
                    saveAnswer();
                    Toast.makeText(requireActivity(), "Submission Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(requireActivity(), "Please type an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Fetching the replies from database into the recyclerview
        approvals = database.getReference("Questions").child("Approvals");
        allquestions = FirebaseDatabase.getInstance().getReference("Questions").child("All Questions").child(postkey).child("Answers");

        FirebaseRecyclerOptions<AnswerMember> options =
                new FirebaseRecyclerOptions.Builder<AnswerMember>()
                        .setQuery(allquestions, AnswerMember.class)
                        .build();

        FirebaseRecyclerAdapter<AnswerMember, Load_Answers> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<AnswerMember, Load_Answers>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Load_Answers holder, int position, @NonNull AnswerMember model) {
                        String currenUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final String postkey = getRef(position).getKey();

                        holder.setAnswer(requireActivity().getApplication(), model.getName(), model.getUid(), model.getAnswer(), model.getTime());
                        holder.approvalchecker(postkey);

                        holder.approvaltv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                approvalchecker = true;
                                approvals.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(approvalchecker.equals(true)) {
                                            if(snapshot.child(postkey).hasChild(currenUID)) {
                                                approvals.child(postkey).child(currenUID).removeValue();
                                            }
                                            else{
                                                approvals.child(postkey).child(currenUID).setValue(true);
                                            }
                                            approvalchecker = false;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });

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

    private void saveAnswer() {

        databaseReferenceall = FirebaseDatabase.getInstance().getReference("Questions").child("All Questions").child(postkey).child("Answers");
        databaseReferenceuser = FirebaseDatabase.getInstance().getReference("Questions").child("User's Questions").child(uid).child(postkey).child("Answers");

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
        final String saveDate = currenDate.format(cdate.getTime());
        member.setAnswer(binding.replytxt.getText().toString());
        member.setTime(saveDate);
        member.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        member.setName(replier_name);
        String id = databaseReferenceall.push().getKey();
        databaseReferenceall.child(id).setValue(member);
        databaseReferenceuser.child(id).setValue(member);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReplyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }




}