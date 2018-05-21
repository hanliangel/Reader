package com.reader.hanli.reader.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.reader.hanli.reader.data.bean.BookDao;
import com.reader.hanli.reader.data.bean.Chapter;
import com.reader.hanli.reader.data.bean.ChapterDao;
import com.reader.hanli.reader.data.bean.DaoMaster;
import com.reader.hanli.reader.data.bean.SearchHistory;
import com.reader.hanli.reader.data.bean.SearchHistoryDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by hanli on 2018/3/20.
 */

public class MyOpenHelper extends DaoMaster.DevOpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.getInstance().migrate(db , BookDao.class , ChapterDao.class ,SearchHistoryDao.class);
    }
}
