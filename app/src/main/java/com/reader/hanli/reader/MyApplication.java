package com.reader.hanli.reader;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;
import com.reader.hanli.reader.data.bean.DaoMaster;
import com.reader.hanli.reader.data.bean.DaoSession;


/**
 * Created by hanli on 2018/2/11.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);
        initGreenDao();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MyApplication getInstance(){
        return mApplication;
    }

    private void initGreenDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "reader");
        SQLiteDatabase writableDatabase =helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(writableDatabase);
        mDaoSession = master.newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
