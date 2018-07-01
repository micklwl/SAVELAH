package com.example.junhu.savelah;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static int notificationID;
    public static final String NOTIFICATION_CHANNEL_ID = "4655";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("checking123", intent.toString());
        Bundle b = intent.getExtras();
        float quantity = b.getFloat("Quantity");
        String name = b.getString("itemName");
        String unit = b.getString("Unit");
        notificationID = b.getInt("Request");
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "NOTIFICATION_CHANNEL_NAME";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            notificationChannel.enableLights(true);
            nm.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Reminder").setContentText("Remember to buy " + name + "(" + quantity + " " + unit + ")" )
                .setSmallIcon(R.drawable.ic_stars_black_24dp);

        // add intent to launch app when user clicks notification.
        Notification notification = builder.build();
        nm.notify(notificationID,notification);
    }
}
