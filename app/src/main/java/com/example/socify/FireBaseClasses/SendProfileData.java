package com.example.socify.FireBaseClasses;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socify.Activities.Registration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SendProfileData {

    UploadTask uploadTask;
    String currentUID;
    StorageReference storageReference;
    DocumentReference documentReference;
    DatabaseReference reference;
    public static HashMap<String, String> profile = new HashMap<>();
    HashMap<String, String> mpPhoneUser = new HashMap<>();
    public static HashMap<String, ArrayList<String>> interests = new HashMap<>();
    DocumentReference mapPhonereference;
    FirebaseAuth auth;
    public SendProfileData(){
        initialization();
    }
    public void initialization() {
        currentUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);

        auth = FirebaseAuth.getInstance();
    }

    public void uploadtoCloud() {

        documentReference.set(profile);
    }

        public void sendImg() {
            if(!Registration.details.getImgUri().equals("")) {
                final StorageReference reference = storageReference.child(Registration.details.getImgUri());
            uploadTask = reference.putFile(Uri.parse(Registration.details.getImgUri()));
            uploadTask.continueWithTask(task -> {

                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUrl = task.getResult();
                    profile.put("ImgUrl", downloadUrl.toString());
                    documentReference.set(profile)
                            .addOnSuccessListener(unused -> Log.i("Img Uploaded", "True"));
                }
            });
        }
        else{
            Log.e("zzz", "No image selected");
            profile.put("ImgUrl", "No Image");
        }

    }

    public void sendName() {
        profile.put("Name", Registration.details.getName());
        uploadtoCloud();
    }

    public void sendpassyear() {
        profile.put("Passing Year", Registration.details.getPassyear());
        uploadtoCloud();
    }

    public void mapUserwithPhone(String username){
        mapPhonereference = FirebaseFirestore.getInstance().collection("MapPhoneUsername").document(Registration.details.getUsername());
        mpPhoneUser.put(username, Objects.requireNonNull(auth.getCurrentUser().getPhoneNumber().substring(auth.getCurrentUser().getPhoneNumber().length()-10)));
        mapPhonereference.set(mpPhoneUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("user mapped", "yes");

            }
        });

        new Thread(() -> {
            AuthCredential credential = EmailAuthProvider.getCredential(auth.getCurrentUser().getPhoneNumber().substring(auth.getCurrentUser().getPhoneNumber().length()-10)+"@gmail.com",Registration.details.getPassword());
            Log.i(auth.getCurrentUser().getPhoneNumber().substring(auth.getCurrentUser().getPhoneNumber().length()-10)+"@gmail.com",Registration.details.getPassword());
            auth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.i("successeful " , "fdjsklafjdklsajfkladsjf");
                    }else {
                        Log.i("Unsuccesseful " , "fdjsklafjdklsajfkladsjf");
                    }
                }
            });
        }).start();


    }

    public void sendUsername() {
        profile.put("Username", Registration.details.getUsername());
        uploadtoCloud();
    }

    public void sendPassword() {
        profile.put("Password", Registration.details.getPassword());
        uploadtoCloud();
    }

    public void sendCollegeName() {
        profile.put("CollegeName", Registration.details.getCollege_name());
        uploadtoCloud();
    }
    public void sendCurrentUID() {
        profile.put("UID", currentUID);
        uploadtoCloud();
    }
    public void sendCourse() {
        profile.put("Course", Registration.details.getCourse());
        profile.put("FollowersCount", "0");
        profile.put("FollowingCount","0");
        profile.put("ProfileStatus", "public");
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        profile.put("token",s);
                        uploadtoCloud();
                    }
                });

    }

    public void sendTags() {
        currentUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);
        interests.put("Tags", Registration.details.getTags());


        documentReference.collection("Interests").document("UserTags").set(interests);
    }

    public void insertintocollege() {
        Log.i("uid",currentUID);
        reference = FirebaseDatabase.getInstance().getReference("College").child(Registration.details.getCollege_name()).child("Profiles");
        reference.child(currentUID).child("uid").setValue(currentUID);

    }
}
