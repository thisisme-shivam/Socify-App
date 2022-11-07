package com.example.socify.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.socify.Classes.College;
import com.example.socify.Classes.Course;
import com.example.socify.InterfaceClass;
import com.example.socify.R;
import com.example.socify.RegistrationFragments.CoursesFragment;
import com.example.socify.RegistrationFragments.GetCollegeFragment;
import com.example.socify.RegistrationFragments.InterestsFragment;
import com.example.socify.RegistrationFragments.ProfilePic;
import com.example.socify.RegistrationFragments.UserNameFragment;
import com.example.socify.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Registration extends AppCompatActivity {


    ActivityRegistrationBinding binding;
    DatabaseReference ref;
    public  ArrayList<College> colleges;
    public HashMap<String,String> profiledetails;
    public HashMap<String,ArrayList<String>> tagMap;
    public  ArrayList<Course> courses;
    InterfaceClass.InterestInterface interestInterface;
    String currentuid,phonenumber;
    boolean registrationComplete;
    static int fragNo = 1;


    void onClickListener()
    {
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.popBackStack();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phonenumber = getIntent().getStringExtra("PhoneNumber");
        courses = new ArrayList<>();
        colleges = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("CollegeNames");
        tagMap = new HashMap<>();
        profiledetails = new HashMap<>();
        currentuid = FirebaseAuth.getInstance().getUid();
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        profiledetails.put("token",s);
                    }
                });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (DataSnapshot snapshot : snapshot.getChildren()) {
                            College college = snapshot.getValue(College.class);
                            colleges.add(college);
                        }
                    }
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Courses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Course course = new Course(Objects.requireNonNull(snap.child("course").getValue()).toString());
                            courses.add(course);
                        }

                    }
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void putImage(){

        if(uri!= null) {
            Log.i("uri", String.valueOf(uri));
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Profile Images").child(String.valueOf(uri));
            UploadTask uploadTask = reference.putFile(uri);
            uploadTask.continueWithTask(task -> {

                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return reference.getDownloadUrl();
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.i("uri",uri.toString());
                    profiledetails.put("ImgUrl", uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    interestInterface.onWorkNotDone();
                    Log.i("photo added","true");
                    Toast.makeText(getApplicationContext(),"Sorry,something went wrong",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            profiledetails.put("ImgUrl", "No Image");

        }


    }


    private void putOtherData(){
        profiledetails.put("UID", currentuid);
        profiledetails.put("FollowersCount",String.valueOf(0));
        profiledetails.put("FollowingCount",String.valueOf(0));
        profiledetails.put("ProfileStatus","public");

        FirebaseFirestore.getInstance().collection("Profiles").document(currentuid)
                .set(profiledetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setUserAccountDetails();
                        send_uid_toCollege();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        interestInterface.onWorkNotDone();
                        Log.i("failed","faild");
                    }
                });
    }


    private void setUserAccountDetails(){
        FirebaseFirestore.getInstance().collection("Profiles").document(currentuid)
                .collection("AccountDetails").document("UserTags").set(tagMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        interestInterface.onWorkDone();
                        registrationComplete = true;
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        interestInterface.onWorkNotDone();
                    }
                });
    }

    private void send_uid_toCollege(){
        FirebaseDatabase.getInstance().getReference()
                .child("College")
                .child(profiledetails.get("College"))
                .child("Profiles")
                .child(currentuid)
                .child("UID").setValue(currentuid).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        interestInterface.onWorkNotDone();
                    }
                });

    }

    private void mapPhoneUsername(){
        Log.i("mapped","true");
        HashMap<String,String> mp = new HashMap<>();
        mp.put(profiledetails.get("Username"), phonenumber);
        FirebaseFirestore.getInstance()
                .collection("MapPhoneUsername")
                .document(profiledetails.get("Username"))
                .set(mp);

            AuthCredential credential = EmailAuthProvider.getCredential(phonenumber+"@gmail.com",password);
            FirebaseAuth.getInstance().getCurrentUser()
                    .linkWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            putOtherData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            interestInterface.onWorkNotDone();
                        }
                    });

    }

    public void registerUser(InterfaceClass.InterestInterface interestInterface) {
        this.interestInterface = interestInterface;
        mapPhoneUsername();
    }



    String password;
    public void setPassword(String password) {
        this.password = password;
    }

    public Uri uri;
    public void setImgUri(Uri uri){
        this.uri =uri;
    }


    @Override
    protected void onStart() {
        super.onStart();
//        NavController navController = Navigation.findNavController(this, R.id.frame_registration);
//        NavigationUI.setupWithNavController(binding.toolbar,navController);
    }

}