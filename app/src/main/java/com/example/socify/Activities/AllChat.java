package com.example.socify.Activities;


import static com.example.socify.HomeFragments.NewsFeedFragment.chattingusers;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socify.Adapters.ChatListAdapter;
import com.example.socify.Classes.ChatListUser;
import com.example.socify.Classes.Person;
import com.example.socify.databinding.ActivityAllChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AllChat extends AppCompatActivity {

    ActivityAllChatBinding binding;
    ArrayList<ChatListUser> chatListUsers;
    ChatListAdapter chatListAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        chatListUsers = new ArrayList<>();


        for(String chatlist: chattingusers) {
            firebaseFirestore.collection("Profiles").document(chatlist).addSnapshotListener((value, error) -> {
                if (value!=null) {
                    Log.e("Chatlist", chatlist);
                    ChatListUser person = new ChatListUser();
                    person.setName(value.getString("Name"));
                    person.setImgUri(value.getString("ImgUrl"));
                    person.setUid(value.getString("UID"));
                    chatListUsers.add(person);
                }
            });
        }

        chatListAdapter = new ChatListAdapter(this, chatListUsers);
        binding.recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.notifyDataSetChanged();
    }

}

