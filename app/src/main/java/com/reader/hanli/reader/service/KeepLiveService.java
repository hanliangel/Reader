package com.reader.hanli.reader.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.bookshelf.BookshelfActivity;

/**
 * Created by hanli on 2018/12/7.
 */

public class KeepLiveService extends Service {

    private static final String LOG_TAG = KeepLiveService.class.getSimpleName();

    public static final int KEEP_LIVE_NOTIFY_ID = 100001;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.iTag(LOG_TAG , "KeepLiveService start !!!");
        if(Build.VERSION.SDK_INT < 18){
            startForeground(KEEP_LIVE_NOTIFY_ID , new Notification());
        } else {
            // 目前这种方式想要隐藏掉notifycation已经不行了，因为7.1以后，Android系统不允许创建两个相同id的通知。
            startService(new Intent(this , KeepLiveInnerService.class));
            startForeground(KEEP_LIVE_NOTIFY_ID , new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 启动带通知的前台服务
     */
    private void startNotificationForeground(){
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent shelfIntent = new Intent(this , BookshelfActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this , 0 , shelfIntent , PendingIntent.FLAG_CANCEL_CURRENT))
                .setLargeIcon(BitmapFactory.decodeResource(getResources() , R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reader is running")
                .setContentText("dont clear this notification")
                .setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        startForeground(KEEP_LIVE_NOTIFY_ID , notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        LogUtils.iTag(LOG_TAG , "KeepLiveService stop !!!");
    }


}
