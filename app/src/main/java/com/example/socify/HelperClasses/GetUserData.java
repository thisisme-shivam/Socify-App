package com.example.socify.HelperClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socify.HomeFragments.VisitProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class GetUserData {
    public String uid,  name, college_name, passyear, course, imgurl, username , followerscount , followingcount , profilestatus;
    public ArrayList<String> tags;
    public ArrayList<String> followinglistuids,followerslistuids;
    VisitProfile.ChagneView changeview;
    public DocumentReference profileinforef,followStatusRef;
    public DocumentSnapshot snap;

    public GetUserData(String uid){
        this.uid = uid;
        loadData();
    }
    public GetUserData(String uid, VisitProfile.ChagneView changeview) {
        this.uid = uid;
        this.changeview = changeview;
        loadData();
    }

    // this function laads all data of the uid provieded following list too.
    private void loadData(){
        tags = new ArrayList<>();
        followinglistuids = new ArrayList<>();
        followerslistuids = new ArrayList<>();
        profileinforef = FirebaseFirestore.getInstance().collection("Profiles").document(uid);
        profileinforef.get()
                .addOnCompleteListener(task -> {
                    snap = task.getResult();

                    if(snap.exists()) {

                        name = snap.getString("Name");
                        college_name = snap.getString("CollegeName");
                        passyear = snap.getString("Passing Year");
                        course = snap.getString("Course");
                        profilestatus = snap.getString("ProfileStatus");
                        try {
                            imgurl = snap.getString("ImgUrl");
                            Log.i("image",imgurl);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        followerscount  = snap.getString("FollowersCount");
                        followingcount = snap.getString("FollowingCount");
                        username = snap.getString("Username");
                        profilestatus = snap.getString("ProfileStatus");
                        // if visiting profile is being loaded
                        if(changeview != null)
                            changeview.dowork();

                    } else{
                        try {
                            throw new Exception("User doesn't exist");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });

        //Loading Tags

        profileinforef = FirebaseFirestore.getInstance().collection("Profiles").document(uid).collection("Interests").document("UserTags");
        profileinforef.get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            if(documentSnapshot.exists()) {
                tags  = (ArrayList<String>)  documentSnapshot.getData().get("Tags");

            }
        });



    }


    public void loadFollowingList(){
        followinglistuids = new ArrayList<>();
        followStatusRef = FirebaseFirestore.getInstance().collection("Profiles")
                .document(uid)
                .collection("AccountDetails")
                .document("FollowingListDoc");
        followStatusRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> list = (ArrayList<String>) task.getResult().get("Followinglist");
                    if(list!=null)
                        followinglistuids.addAll(list);
                }
            }
        });

        followStatusRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { if(task.isSuccessful()){
                ArrayList<String> list = (ArrayList<String>) task.getResult().get("FollowersList");
                if(list!=null)
                    followerslistuids.addAll(list);
            }

            }
        });
    }
}
