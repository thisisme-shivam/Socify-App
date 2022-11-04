package com.example.socify;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.socify.Activities.Home;
import com.example.socify.Classes.NotificationMember;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendNotification {

    private static String url = "https://fcm.googleapis.com/fcm/send";


    private static void sendFollowNotification(String sendToUid,String message){
        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
        String date = currenDate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        String time = currentTime.format(ctime.getTime());

        NotificationMember notification = new NotificationMember();
        notification.setUserUid(Home.getUserData.uid);
        notification.setUsername(Home.getUserData.username);
        notification.setType("follow");
        notification.setImgurl(Home.getUserData.imgurl);
        notification.setTime(time);
        notification.setDate(date);
        notification.setInfo(message);
        FirebaseDatabase.getInstance().getReference()
                .child("College").child(Home.getUserData.college_name)
                .child("Notifications")
                .child(sendToUid)
                .push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("xax", "asas");
                    }
                });
    }
    public static void sendFollowNotification(Context context,  String token,String sendToUid){
        sendFollowNotification(sendToUid,"has started following you.");
        sendToFcm(context,token,"has started following you.");
    }

    public static void sendLikeNotification(Context context, String token, String sendToUid, String postid) {
        sendPostNotification(sendToUid,postid,"has liked your post");
        sendToFcm(context,token,"has liked your post");
    }
    public static void sendCommentNotification(Context context, String token, String sendToUid, String postid){
        sendPostNotification(sendToUid,postid,"has commented on  your post");
        sendToFcm(context,token,"has commented on  your post");
    }

    private static void sendPostNotification(String sendToUid,String postid, String message) {
        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
        String date = currenDate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        String time = currentTime.format(ctime.getTime());

        NotificationMember notification = new NotificationMember();
        notification.setUserUid(postid);
        notification.setUsername(Home.getUserData.username);
        notification.setType("post");
        notification.setImgurl(Home.getUserData.imgurl);
        notification.setTime(time);
        notification.setDate(date);
        notification.setInfo(message);
        FirebaseDatabase.getInstance().getReference()
                .child("College").child(Home.getUserData.college_name)
                .child("Notifications")
                .child(sendToUid)
                .push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("xax", "asas");
                    }
                });


    }


    static void sendToFcm(Context context,String token,String message){
        try {
            String url = "https://fcm.googleapis.com/fcm/send";
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject data = new JSONObject();
            data.put("title", "Socify");
            data.put("body",Home.getUserData.username +" "+message);

            JSONObject notificationData = new JSONObject();
            notificationData.put("notification",data);

            notificationData.put("to",token);

            JsonObjectRequest request = new JsonObjectRequest(url, notificationData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("success",response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error",error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> mp = new HashMap<>();
                    mp.put("name","blash");
                    String key = "key=AAAAh5GYjaw:APA91bGoEuQV6O9xEZLY_q-DD8MrXgWkEghdmFxbeqWCPVoTBBLriRbUMDdw_L_AhsyBepvR9C0oJaHc82cCivd7wpfgN22yGPZATZGTsTkbhjhtMl4u-0aHpTN6bd3yUsKgoBa7Z9cW";
                    mp.put("Authorization",key);
                    mp.put("Content-Type","application/json");
                    return mp;
                }
            };
            queue.add(request);
        }catch (Error | JSONException e){
            Log.i("Failed","at some point");
            e.printStackTrace();
        }
    }
}
