package com.example.socify.HelperClasses;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class GetUserData {
    public String uid,  name, college_name, passyear, course, imgurl ="", username;
    public ArrayList<String> tags;
    public Map<String, Object> tagmap;
    public GetUserData(String uid){
        this.uid = uid;
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(uid);

        documentReference.get()
                .addOnCompleteListener(task -> {

                    if(task.getResult().exists()) {
                        name = task.getResult().getString("Name");
                        college_name = task.getResult().getString("CollegeName");
                        passyear = task.getResult().getString("Passing Year");
                        course = task.getResult().getString("Course");
                        try {
                            imgurl = task.getResult().getString("ImgUrl");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        username = task.getResult().getString("Username");

                    } else{
                        try {
                            throw new Exception("User doesn't exist");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });

        //Loading Tags
        tags = new ArrayList<>();
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(uid).collection("Interests").document("UserTags");

        documentReference.get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            if(documentSnapshot.exists()) {
                tagmap = documentSnapshot.getData();
                assert tagmap != null;
                String toadd = "";
                String s = tagmap.get("Tags").toString();
                for(int i=1;i<s.length();i++){
                    if(s.charAt(i)==',' || s.charAt(i) == ']'){
                        System.out.println(toadd);
                        tags.add(toadd.trim());
                        toadd = "";
                    }else{
                        toadd = toadd.concat(String.valueOf(s.charAt(i)));
                    }
                }
                System.out.println(s);

            }
        });
    }
}
