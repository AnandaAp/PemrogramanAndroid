package com.android.helloworld;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class WifiBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())){
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        onWifiNotification("Wifi is On",context);
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        onWifiNotification("Wifi is off",context);
                    }
                    break;
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onWifiNotification(String WIFISTATE,Context context){
        String CHANEL_ID = "WIFI_NOTIFICATION_ID";
        int NOTIFICATION_ID = 0;
        NotificationChannel mChannel = new NotificationChannel(
                CHANEL_ID,"Wifi Channel",NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);
        Notification builder = new NotificationCompat.Builder(context,CHANEL_ID)
                .setSmallIcon(R.drawable.ic_wifi)
                .setContentTitle("Wifi Notification")
                .setAutoCancel(true)
                .setContentText(WIFISTATE)
                .build();
        notificationManager.notify(NOTIFICATION_ID,builder);
    }
}
