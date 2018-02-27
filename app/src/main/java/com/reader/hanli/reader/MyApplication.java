package com.reader.hanli.reader;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


/**
 * Created by hanli on 2018/2/11.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);
//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MyApplication getInstance(){
        return mApplication;
    }
}
