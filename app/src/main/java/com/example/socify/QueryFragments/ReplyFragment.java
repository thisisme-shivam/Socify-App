package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.Classes.AnswerMember;
import com.example.socify.R;
import com.example.socify.databinding.FragmentReplyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            String name = task.getResult().getString("Name");
                            binding.question.setText(question);
                            binding.username.setText("Posted by " +  name);
                            binding.tag.setText(tag);
                        }
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


        //Replies


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