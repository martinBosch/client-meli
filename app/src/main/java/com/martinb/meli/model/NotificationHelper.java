package com.martinb.meli.model;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.martinb.meli.R;

public class NotificationHelper {

    private static final String CHANNEL_ID = "Meli";

    public static void displayNotification(Context context, String title, String body) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, mBuilder.build());
    }
}
