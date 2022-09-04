package com.example.socify.QueryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.Classes.QuestionsMember;
import com.example.socify.R;
import com.example.socify.databinding.ActivityQnBinding;
import com.example.socify.databinding.FragmentAskQueryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Ask_QueryFragment extends Fragment {

    FragmentAskQueryBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference AllQuestions, UserQuestions;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUID = firebaseUser.getUid();
    String name, url, UID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    QuestionsMember member;

    public void setonclicklisteners() {
        binding.askbtn.setOnClickListener(v -> {
            String question = binding.questiontext.getText().toString();
            String tag = binding.categories.getText().toString();

            Calendar cdate = Calendar.getInstance();
            SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
            final String saveDate = currenDate.format(cdate.getTime());

            Calendar ctime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
            final String saveTime = currentTime.format(ctime.getTime());

            String time = saveDate + ":" + saveTime;

            if(question!=null) {
                member.setName(name);
                member.setQuestion(question);
                member.setTime(time);
                member.setUserid(UID);
                member.setUrl(url);
                member.setTag(tag);

                String id = UserQuestions.push().getKey();
                UserQuestions.child(id).setValue(member);

                String child = AllQuestions.push().getKey();
                member.setKey(id);
                AllQuestions.child(child).setValue(member);
                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(requireActivity(), "Please enter the questions", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            name = task.getResult().getString("Name");
                            url = task.getResult().getString("ImgUrl");
                            UID = task.getResult().getString("UID");
                        }
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
        binding = FragmentAskQueryBinding.inflate(inflater, container, false);

        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);
        AllQuestions = database.getReference("Questions").child("All Questions");
        UserQuestions = database.getReference("Questions").child("User's Questions").child(currentUID);
        member = new QuestionsMember();
        setonclicklisteners();

        return binding.getRoot();
    }
}