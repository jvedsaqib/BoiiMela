package com.gssproductions.boiimela;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMRequest {


    private static final String FCM_API = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "";

    String deviceToken, title,
            message;

    Context context;
    Activity mActivity;

    public FCMRequest(String deviceToken, String title, String message, Context context, Activity mActivity) {
        this.deviceToken = deviceToken;
        this.title = title;
        this.message = message;
        this.context = context;
        this.mActivity = mActivity;
    }

    public void sendNotification() {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        JSONObject notificationObject = new JSONObject();
        JSONObject messageObject = new JSONObject();
        try {
            System.out.println("Title -> " + title);
            System.out.println("Body -> " + message);

            messageObject.put("title", title);
            messageObject.put("body", message);
            notificationObject.put("to", deviceToken);
            notificationObject.put("data", messageObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FCM_API, notificationObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key=" + SERVER_KEY);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}

