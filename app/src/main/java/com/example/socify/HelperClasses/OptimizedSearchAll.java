package com.example.socify.HelperClasses;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.PersonAdapter;
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
    boolean loaded;
    Thread th;

    public OptimizedSearchAll(SearchAll searchAll){
        allusers = new ArrayList<>();
        following_list = new ArrayList<>();
        this.searchAll = searchAll;

        ref = FirebaseDatabase.getInstance().getReference("College").child(Home.college_name).child("Profiles");
        Log.i("collegeName", Home.college_name);
        th = new Thread(() -> ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot s: task.getResult().getChildren()){
                    if( !s.getKey().equals(Home.uid))
                        getOtherdata(s.getKey());
                }
                searchAll.hand.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }));

        new Thread(new Runnable() {
            @Override
            public void run() {
                checkstatus();
            }
        }).start();
    }


    private void getOtherdata(String uid) {
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(uid);

        documentReference.get().addOnCompleteListener(task -> {
                    if(task.getResult().exists()) {
                        String name = task.getResult().getString("Name");
                        String username = task.getResult().getString("Username");
                        imguri = task.getResult().getString("ImgUrl");
                        Log.i("values",name + " " + username + " " + imguri);
                        if(following_list.contains(uid)){
                            allusers.add(new Person(name,username,"YES",imguri));
                        }else{
                            allusers.add(new Person(name,username,"NO",imguri));
                        }
                    }
        });
    }

    private void checkstatus() {
        ref2 = FirebaseFirestore.getInstance().collection("Profiles").document(Home.uid).collection("Following").document("following");
        ref2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String,Object> has =  task.getResult().getData();
                String following = "";
                if(has != null){
                    StringBuilder s = new StringBuilder();
                    for(int i=2;i<has.values().toString().length()-2;i++){
                        char c = has.values().toString().charAt(i);
                        if(c == ','){
                            following_list.add(s.toString());
                            Log.i("String is " , s.toString());
                            s = new StringBuilder();
                        }else{
                            s.append(c);
                        }
                    }

                }
                th.start();
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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Person p : allusers) {
                        if (p.getUsername().contains(newtext))
                            filteredlist.add(p);
                    }
                    
                    searchAll.hand.post(new Runnable() {
                        @Override
                        public void run() {
                            searchAll.personAdapter.filterlist(filteredlist);
                            filteredlist = new ArrayList<>();
                        }
                    });
                }
            }).start();
        }
    }
}
