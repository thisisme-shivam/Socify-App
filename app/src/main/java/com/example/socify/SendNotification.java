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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotification {

    private static String url = "https://fcm.googleapis.com/fcm/send";
    public static void sendFollowNotification(Context context, String uid, String username, String token){
        try {
            String url = "https://fcm.googleapis.com/fcm/send";
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject data = new JSONObject();
            data.put("title", username);
            data.put("body",username +" "+"has started following you.");

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
