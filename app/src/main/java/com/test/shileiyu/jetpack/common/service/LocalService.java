package com.test.shileiyu.jetpack.common.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.ThreadPool;

public class LocalService extends Service {
    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("com.test.shileiyu.jetpack", "mychannel", NotificationManager.IMPORTANCE_HIGH);
            String id = channel.getId();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
            builder.setContentText("使用了startForeground");
            builder.setWhen(System.currentTimeMillis());
            builder.setSmallIcon(R.mipmap.gou1);
            builder.setSubText("这是一则测试文案");
            Notification build = builder.build();
            startForeground(0x01, build);
        }


        ThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10000);
                Log.d("LocalService", "sleep 10秒");
                LocalService.this.stopForeground(true);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
