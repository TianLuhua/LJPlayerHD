package com.example.lj.ljplayerhd.main.offline.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lj.ljplayerhd.utils.SLogUtil;

/**
 * Created by tianluhua on 2017/1/10 0010.
 */

public class LJDbOpenHelper extends SQLiteOpenHelper {

    public static final String AT_DB_NAME = "downLoadTask.db";
    public static final int AT_DB_VERSION = 1;

    public LJDbOpenHelper(Context context) {
        super(context, AT_DB_NAME, null, AT_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SLogUtil.e("tlh", "数据库下载列表SQL：" + LJDb.DownLoadTaskTable.CREATE_DOWNLOADTABLE);
        sqLiteDatabase.execSQL(LJDb.DownLoadTaskTable.CREATE_DOWNLOADTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
