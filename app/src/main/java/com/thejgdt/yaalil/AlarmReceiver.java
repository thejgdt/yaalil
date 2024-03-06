package com.thejgdt.yaalil;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Buat notifikasi
        Notification.Builder builder = new Notification.Builder(context).setSmallIcon(R.drawable.ic_logo)  // ganti dengan icon aplikasi Anda
                .setContentTitle("Pengingat").setContentText("Ini adalah notifikasi yang dijadwalkan untuk hari Kamis pukul 19:00").setAutoCancel(true);

        // Tampilkan notifikasi
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}