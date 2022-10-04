package com.example.socify.HelperClasses;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class CurrentUser {
    static String name, collegeName , course , imgUrl , passing_Year , uid , username;


    public CurrentUser() {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Profiles").document(FirebaseAuth.getInstance().getUid());

        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Map<String, Object> mp = task.getResult().getData();
                assert mp != null;
                collegeName = (String) mp.get("CollegeName");
                course = (String) mp.get("Course");
                imgUrl = (String) mp.get("ImgUrl");
                name = (String) mp.get("Name");
                passing_Year = (String) mp.get("Passing Year");
                username = (String) mp.get("Username");
                uid = (String) mp.get("UID");
            }
        });
    }

}
