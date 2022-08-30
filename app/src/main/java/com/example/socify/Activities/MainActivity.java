package com.example.socify.Activities;

import static android.util.Log.i;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.socify.R;
import com.example.socify.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.grpc.internal.JsonParser;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore fstore;
    DatabaseReference ref ;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.socify.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        ref = FirebaseDatabase.getInstance().getReference();
//
//        String string = "";
//        try {
//            InputStream inputStream = getAssets().open("courses.json");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            string = new String(buffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        try {
//            JSONObject obj = new JSONObject(string);
//            JSONArray m_jArry = obj.getJSONArray("courses");
//
//            HashMap<String, String> m_li;
//
//
//            int j=0;
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(i);
//                Log.i("college name is ",jo_inside.getString("courses_offered"));
//                HashMap<String,String> collegename = new HashMap<>();
//                collegename.put("course",jo_inside.getString("courses_offered"));
//
//
//                ref.child("Courses").push().setValue(collegename).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful())
//                            Log.i("fjasdfjl","fjklasdjflksdjf");
//                    }
//                });
//
//
//            }
//            Log.i("value",Integer.toString(j));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }

}