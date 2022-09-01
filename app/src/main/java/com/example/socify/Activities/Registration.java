package com.example.socify.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socify.Classes.College;
import com.example.socify.FireBaseClasses.UserDetails;
import com.example.socify.Fragement_registration.ProfilePic;
import com.example.socify.R;
import com.example.socify.databinding.ActivityRegistrationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {

    ArrayList<College> colleges;
    public static int a =10;
    ActivityRegistrationBinding binding;
    DatabaseReference ref;
    public static UserDetails details = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportFragmentManager().beginTransaction().add(R.id.frame_registration, new ProfilePic()).commit();

    }
}