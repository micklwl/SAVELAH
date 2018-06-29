//package com.example.junhu.savelah.notifications;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.NotificationCompat;
//
//import com.example.junhu.savelah.R;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import java.util.Random;
//
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
//    private static final String IMAGE_URL_EXTRA = "imageUrl";
//    private static final String ADMIN_CHANNEL_ID ="admin_channel";
//    private NotificationManager notificationManager;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        //Setting up Notification channels for android O and above
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            setupChannels();
//        }
//        int notificationId = new Random().nextInt(60000);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_info_black_24dp)  //a resource for your custom small icon
//                .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
//                .setContentText(remoteMessage.getData().get("message")) //ditto
//                .setAutoCancel(true)  //dismisses the notification on click
//                .setSound(defaultSoundUri);
//
//        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setupChannels(){
//        CharSequence adminChannelName = "";
//        String adminChannelDescription = "";
//
//        NotificationChannel adminChannel;
//        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
//        adminChannel.setDescription(adminChannelDescription);
//        adminChannel.enableLights(true);
//        adminChannel.setLightColor(Color.RED);
//        adminChannel.enableVibration(true);
//        if (notificationManager != null) {
//            notificationManager.createNotificationChannel(adminChannel);
//        }
//    }
//
//}
