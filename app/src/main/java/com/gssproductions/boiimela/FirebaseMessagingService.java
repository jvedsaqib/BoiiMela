package com.gssproductions.boiimela;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    String body = "";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.P){
            r.setLooping(false);
        }

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 300, 300, 300};
        v.vibrate(pattern, -1);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "100");


        Intent resultIntent = new Intent(this, BaseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_MUTABLE);


//        builder.setContentTitle(message.getNotification().getTitle());
//        builder.setContentText(message.getNotification().getBody());

        try
        {
            Map<String, String> params = message.getData();
            JSONObject object = new JSONObject(params);
            // Log.e("JSON_OBJECT", object.toString());

            body = object.get("body").toString();

        }catch (Exception e){

        }

        builder.setContentTitle("Chat Alert!");
        builder.setContentText("You have a new message.");
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.icon);
        builder.setPriority(Notification.PRIORITY_MAX);


        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            System.out.println("Inside IF");
            String channelId = "chat";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Chat Message",
                    NotificationManager.IMPORTANCE_HIGH
            );
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        mNotificationManager.notify(100, builder.build());


    }
}
