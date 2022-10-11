package com.example.socify.HelperClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socify.Activities.Home;
import com.example.socify.Classes.Person;
import com.example.socify.HomeFragments.SearchAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class OptimizedSearchAll {
    public ArrayList<Person> allusers;
    DatabaseReference ref;
    DocumentReference  documentReference;
    ArrayList<String> following_list ;
    DocumentReference ref2;
    private String imguri;
    SearchAll searchAll ;
    boolean stop;


    public OptimizedSearchAll(SearchAll searchAll){
        allusers = new ArrayList<>();
        following_list = new ArrayList<>();
        this.searchAll = searchAll;
        // checking the user following list
        ref = FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Profiles");
        ref.get().addOnCompleteListener(task -> {

            for(DataSnapshot s: task.getResult().getChildren()){
                if( !s.getKey().equals(Home.getUserData.uid))
                    getOtherdata(s.getKey());
            }

        });



    }


    // getting user details from like name , username , photo using uid
    private void getOtherdata(String uid) {
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(uid);
        Log.i("myuid", Home.getUserData.uid);

        documentReference.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()) {
                String name = task.getResult().getString("Name");
                String username = task.getResult().getString("Username");
                imguri = task.getResult().getString("ImgUrl");
                Log.i("values",uid);

                if(Home.getUserData.followinglistuids.contains(uid)){
                    allusers.add(new Person(name,username,true,imguri,uid));
                }else{
                    allusers.add(new Person(name,username,false,imguri,uid));
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
