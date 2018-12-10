package com.reader.hanli.reader.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.blankj.utilcode.util.LogUtils;

import static com.reader.hanli.reader.service.KeepLiveService.KEEP_LIVE_NOTIFY_ID;

/**
 * api 大于18 时候用到的内部service，启动后立即杀死
 */
public class KeepLiveInnerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.iTag("KeepLiveInnerService" , "KeepLiveInnerService onStartCommand");
        startForeground(KEEP_LIVE_NOTIFY_ID, new Notification());
        stopForeground(true);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}