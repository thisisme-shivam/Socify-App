package com.example.socify.HelperClasses;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socify.HomeFragments.VisitProfile;
import com.example.socify.InterfaceClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    InterfaceClass.LoadDataInterface changeview;
    public DocumentReference profileinforef,followStatusRef;
    public DocumentSnapshot snap;

    InterfaceClass.VisitProfileInterface visitProfileinterface;

    public GetUserData(String uid){
        this.uid = uid;
        loadData();
    }

    public GetUserData(String uid, InterfaceClass.LoadDataInterface changeview) {
        this.uid = uid;
        this.changeview = changeview;
        loadData();
    }

    public GetUserData(String uid,InterfaceClass.VisitProfileInterface visitProfileinterface){
        this.uid = uid;
        this.visitProfileinterface = visitProfileinterface;
        loadData();
    }

    // this function laads all data of the uid provieded following list too.
    private void loadData(){
        tags = new ArrayList<>();
        followinglistuids = new ArrayList<>();
        followerslistuids = new ArrayList<>();
        profileinforef = FirebaseFirestore.getInstance().collection("Profiles").document(uid);

        followStatusRef = FirebaseFirestore.getInstance().collection("Profiles")
                .document(uid)
                .collection("AccountDetails")
                .document("FollowingListDoc");
        profileinforef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        snap = value;
//                        FirebaseMessaging.getInstance().getToken()
//                                .addOnSuccessListener(new OnSuccessListener<String>() {
//                                    @Override
//                                    public void onSuccess(String s) {
//                                        snap.getReference().update("token",s);
//                                    }
//                                });
                        if(value != null) {
                            name = value.getString("Name");
                            college_name = value.getString("CollegeName");
                            passyear = value.getString("Passing Year");
                            course = value.getString("Course");
                            profilestatus = value.getString("ProfileStatus");
                            try {
                                imgurl = value.getString("ImgUrl");
                                Log.i("image",imgurl);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            token = value.getString("token");
                            followerscount  = value.getString("FollowersCount");
                            followingcount = (String) value.getString("FollowingCount");
                            username = (String) value.getString("Username");
                            // if visiting profile is being loaded

                        } else{
                            try {
                                throw new Exception("User doesn't exist");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


        loadFollowingList();
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

        followStatusRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null ){
                    Map<String,Object> mp = value.getData();
                    if(mp != null) {
                        ArrayList<String> followinglist = (ArrayList<String>) mp.get("FollowingList");
                        Log.i("vlaueof ",mp.toString());
                        followinglistuids = followinglist;

                        ArrayList<String> followerlist = (ArrayList<String>) mp.get("FollowerList");

                        followerslistuids = followerlist;

                        if(visitProfileinterface!=null){
                            visitProfileinterface.onWorkDone();
                        }


                    }
                }
                if(changeview != null)
                    changeview.onWorkDone();
            }
        });

    }
}
