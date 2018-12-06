package com.martinb.meli.model;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MeliFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() == null) {
            return;
        }
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        NotificationHelper.displayNotification(this, title, body);
    }
}
