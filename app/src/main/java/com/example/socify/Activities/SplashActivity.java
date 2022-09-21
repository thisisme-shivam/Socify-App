package com.example.socify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Classes.College;
import com.example.socify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    DatabaseReference ref;
    public static ArrayList<College> colleges;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finishAffinity();
        }

        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, SlideScreen.class));
                    finish();
                }
            }, 1000);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}