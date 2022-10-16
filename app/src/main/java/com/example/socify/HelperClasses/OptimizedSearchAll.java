package com.example.socify.HelperClasses;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socify.Activities.Home;
import com.example.socify.Classes.Person;
import com.example.socify.HomeFragments.SearchAll;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashSet;

public class OptimizedSearchAll {
    public ArrayList<Person> allusers;
    DatabaseReference profileref;
    DocumentReference  documentReference;
    SearchAll searchAll ;
    boolean stop;
    HashSet<String> personsuid;

    public OptimizedSearchAll(SearchAll searchAll){
        allusers = new ArrayList<>();
        personsuid = new HashSet<>();
        this.searchAll = searchAll;
        // checking the user following list
        profileref = FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Profiles");

        profileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    if( !s.getKey().equals(Home.getUserData.uid))
                        getOtherdata(s.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    // getting user details  like name , username , photo using uid
    private void getOtherdata(String uid) {
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(uid);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value != null){
                    String name = (String) value.get("Name");
                    String username = (String) value.get("Username");
                    String imguri =  (String) value.get("ImgUrl");
                    Log.i("values",uid);

                    if(Home.getUserData.followinglistuids.contains(uid)){
                        allusers.add(new Person(name,username,true,imguri,uid));
                    }else {
                        allusers.add(new Person(name, username, false, imguri, uid));
                    }
                }
            }
        });
    }




    public void stopSearch() {
        stop = true;
    }

    ArrayList<Person> filteredlist = new ArrayList<>();

    public void startSearch(String newtext) {

        if(newtext.isEmpty())
            searchAll.personAdapter.filterlist(new ArrayList<>());
        else {
            new Thread(() -> {
                for (Person p : allusers) {
                    if(p.getUsername().toLowerCase().startsWith(newtext.toLowerCase()))
                        filteredlist.add(p);
                }

                searchAll.hand.post(() -> {
                    searchAll.personAdapter.filterlist(filteredlist);
                    filteredlist = new ArrayList<>();
                });
            }).start();
        }
    }
}
