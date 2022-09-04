package com.example.socify.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socify.Classes.College;
import com.example.socify.Classes.Course;
import com.example.socify.FireBaseClasses.UserDetails;
import com.example.socify.R;
import com.example.socify.RegistrationFragments.ProfilePic;
import com.example.socify.databinding.ActivityRegistrationBinding;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Objects;

public class Registration extends AppCompatActivity {

    ArrayList<College> colleges;

    ActivityRegistrationBinding binding;
    DatabaseReference ref;
    public static UserDetails details = new UserDetails();
    public static ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ref = FirebaseDatabase.getInstance().getReference("Courses");
        courses = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (DataSnapshot snap : snapshot.getChildren()) {

                            Course course = new Course(Objects.requireNonNull(snap.child("course").getValue()).toString());
                            courses.add(course);
                            Log.i("course name ", course.getcoursename());
                        }

                    }
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.frame_registration, new ProfilePic()).commit();

    }
}