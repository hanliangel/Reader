package com.reader.hanli.reader;

import android.app.Application;

/**
 * Created by hanli on 2018/2/11.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MyApplication getInstance(){
        return mApplication;
    }
}
