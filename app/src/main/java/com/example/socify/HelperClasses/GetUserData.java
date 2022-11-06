package com.example.socify.HelperClasses;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socify.HomeFragments.VisitProfile;
import com.example.socify.InterfaceClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Map;

public class GetUserData {
    public String uid,  name, college_name,token, passyear, course, imgurl, username , followerscount , followingcount , profilestatus;
    public ArrayList<String> tags;
    public ArrayList<String> followinglistuids,followerslistuids;
    public DocumentReference profileinforef,followerListRef,followingListRef;
    public DocumentSnapshot snap;

    InterfaceClass.VisitProfileInterface visitProfileinterface;
    InterfaceClass.LoadDataInterface currentUserInterface;


    public GetUserData(String uid, InterfaceClass.LoadDataInterface changeview) {
        this.uid = uid;
        this.currentUserInterface = changeview;
        initialize();
        loadDataForCurrentUser();
    }

    public GetUserData(String uid,InterfaceClass.VisitProfileInterface visitProfileinterface){
        this.uid = uid;
        this.visitProfileinterface = visitProfileinterface;
        initialize();
        loadDataVisitUser();
    }

    private void initialize() {
        tags = new ArrayList<>();
        followinglistuids = new ArrayList<>();
        followerslistuids = new ArrayList<>();
        profileinforef = FirebaseFirestore.getInstance().collection("Profiles").document(uid);

        followingListRef = FirebaseFirestore.getInstance().collection("Profiles")
                .document(uid)
                .collection("AccountDetails")
                .document("FollowingList");

        followerListRef = FirebaseFirestore.getInstance().collection("Profiles")
                .document(uid)
                .collection("AccountDetails")
                .document("FollowerList");
    }

    private void loadDataVisitUser() {
        profileinforef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot value) {
                snap = value;

                if(value != null) {
                    name = value.getString("Name");
                    college_name = value.getString("College");
                    passyear = value.getString("Passing Year");
                    course = value.getString("Course");
                    profilestatus = value.getString("ProfileStatus");
                    imgurl = value.getString("ImgUrl");
                    token = value.getString("token");
                    followerscount  = value.getString("FollowersCount");
                    followingcount =  value.getString("FollowingCount");
                    username = value.getString("Username");

                    if(visitProfileinterface!=null){
                        visitProfileinterface.onWorkDone();
                    }
                } else{
                    if(visitProfileinterface != null)
                        visitProfileinterface.onWorkNotDone();
                    try {
                        throw new Exception("User doesn't exist");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // this function laads all data of the uid provided following list too.
    private void loadDataForCurrentUser(){


        profileinforef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        snap = value;

                        if(value != null) {
                            name = value.getString("Name");
                            college_name = value.getString("College");
                            passyear = value.getString("Passing Year");
                            course = value.getString("Course");
                            profilestatus = value.getString("ProfileStatus");
                            imgurl = value.getString("ImgUrl");
                            token = value.getString("token");
                            followerscount  = value.getString("FollowersCount");
                            followingcount =  value.getString("FollowingCount");
                            username = value.getString("Username");
                            // if visiting profile is being loaded

                        } else{
                            if(currentUserInterface != null)
                                currentUserInterface.onWorkNotDone();
                            try {
                                throw new Exception("User doesn't exist");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


        loadFollowingList();
        loadTags();
    }

    public void loadTags(){
        profileinforef = FirebaseFirestore.getInstance().collection("Profiles").document(uid).collection("AccountDetails").document("UserTags");
        profileinforef.get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            if(documentSnapshot.exists()) {
                tags  = (ArrayList<String>)  documentSnapshot.getData().get("Tags");
            }
        });
    }


    public void loadFollowingList(){

        followingListRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getData() != null ){
                    Map<String,Object> mp = value.getData();
                    if(mp.get("FollowingList") != null) {
                        followinglistuids = (ArrayList<String>) mp.get("FollowingList");
                        if(currentUserInterface != null) {
                            currentUserInterface.onWorkDone();
                            currentUserInterface = null;
                        }


                    }
                }

            }
        });

    }

    public void loadFollowersList(){
        followerListRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getData() != null ){
                    Map<String,Object> mp = value.getData();
                    if(mp.get("FollowersList") != null)
                        followerslistuids = (ArrayList<String>) mp.get("FollowersList");
                }
            }
        });
    }
}
