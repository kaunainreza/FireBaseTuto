package com.example.firebasetuto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NoteMsgService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("RefreshedToken",token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getNotification() != null) {
            pushNotification(
                    message.getNotification().getTitle(),
                    message.getNotification().getBody()
            );
        }
    }
    private void pushNotification(String title, String Msg) {

        NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        final String CHANNEL_ID="push_noti";

        Intent iNotify = new Intent(this,MainActivity.class);
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivity(this,100,iNotify,PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Custom Channel";
            String description = "Channel for Push Notification";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel= new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            if (nm!=null)
                nm.createNotificationChannel(channel);

            notification= new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pi)
                    .setContentTitle(title)
                    .setSubText(Msg)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();
        }
        else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setSubText(Msg)
                    .build();
        }
        if (nm!=null)
            nm.notify(1,notification);

    }
}
