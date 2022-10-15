package com.example.socify.QueryFragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.QnA;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Ask_QueryFragment extends Fragment {

    FragmentAskQueryBinding binding;
    private static final int PICK_FILE=1;
    private Uri questionimgURI;
    String type;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Questions;

    //Fragment after successful question
    QuestionsMember member;

    public void setonclicklisteners() {

        binding.questiontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });


        binding.askbtn.setOnClickListener(v -> {
            String question = binding.questiontext.getText().toString().trim();
            String tag = binding.categories.getText().toString();

            Calendar cdate = Calendar.getInstance();
            SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
            final String saveDate = currenDate.format(cdate.getTime());

            Calendar ctime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
            final String saveTime = currentTime.format(ctime.getTime());

            String time = saveDate + ":" + saveTime;

            if(!question.isEmpty() && !tag.isEmpty()) {
                member.setUsername(Home.getUserData.username);
                member.setQuestion(question);
                member.setTime(time);
                member.setUserid(Home.getUserData.uid);
                member.setUrl(Home.getUserData.imgurl);
                member.setTag(tag);

                String postid = Questions.push().getKey();
                member.setKey(postid);
                Questions.child(member.getTag().replaceAll("[^A-Za-z]+", "").toLowerCase()).child(Home.getUserData.uid).child(postid).setValue(member);

                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                QueryTagFragment queryTagFragment = new QueryTagFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.queryFragmentLoader, queryTagFragment).commit();
            }
            else if(question.isEmpty()){
                Toast.makeText(requireActivity(), "Please enter the question", Toast.LENGTH_SHORT).show();
            }
            else if(tag.isEmpty()) {
                Toast.makeText(requireActivity(), "Please select a tag", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setonclicklisteners();

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
        Questions = database.getReference("College").child(Home.getUserData.college_name).child("Questions");
        member = new QuestionsMember();
        return binding.getRoot();
    }

    @SuppressLint("IntentReset")
    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == PICK_FILE && data != null) {
            questionimgURI = data.getData();
            Log.e("Selected URI", String.valueOf(questionimgURI));
            if(questionimgURI.toString().contains("image")) {
                Picasso.get().load(questionimgURI).into(binding.queryimg);
                binding.queryimg.setVisibility(View.VISIBLE);
                binding.questiontext.setVisibility(View.GONE);
                type = "image";
                Log.e("Selected URI", String.valueOf(questionimgURI));
            }

            else
                Toast.makeText(requireActivity(), "No File Selected", Toast.LENGTH_SHORT).show();
        }

    }


}