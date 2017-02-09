package com.example.lj.ljplayerhd.main.offline.download.db;

import android.content.Context;

import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;
import com.example.lj.ljplayerhd.main.offline.download.tools.Logger;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tianluhua on 2017/1/10 0010.
 */

public class LJDbManager {

    private static final String TAG = "ATDbManager";
    // rx响应式数据库,
    private BriteDatabase briteDatabase;

    public LJDbManager(Context context) {
        LJDbOpenHelper dbOpenHelper;
        // sqlbrite 初始化,构造出响应式数据库,添加log
        SqlBrite sqlBrite;
        sqlBrite = SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Logger.wtf(TAG, "log: >>>>" + message);
            }
        });
        // 原生的sqllitehelper 用来建立数据库和数据表,以及构造,rx响应式数据库
        dbOpenHelper = new LJDbOpenHelper(context);
        // 执行slqbirte 构造数据库的语句
        briteDatabase = sqlBrite.wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
        briteDatabase.setLoggingEnabled(true);
    }

    public Observable<List<DownLoadTask>> queryDownLoadTask() {
        return briteDatabase
                .createQuery(LJDb.DownLoadTaskTable.TABLE_NAME, "SELECT * FROM " + LJDb.DownLoadTaskTable.TABLE_NAME)
                .mapToList(LJDb.DownLoadTaskTable.PERSON_MAPPER);

    }

    public Observable<List<DownLoadTask>> queryDownLoadTaskByName(String name) {
        return briteDatabase.createQuery(LJDb.DownLoadTaskTable.TABLE_NAME, "SELECT * FROM "
                        + LJDb.DownLoadTaskTable.TABLE_NAME
                        + " WHERE "
                        + LJDb.DownLoadTaskTable.COLUMN_NAME
                        + " = ?"
                , name)
                .mapToList(LJDb.DownLoadTaskTable.PERSON_MAPPER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public int replaceDownLoadTasks(DownLoadTask downLoadTask) {
        return briteDatabase.update(LJDb.DownLoadTaskTable.TABLE_NAME, LJDb.DownLoadTaskTable.toContentValues(downLoadTask), LJDb.DownLoadTaskTable.COLUMN_ID + "=" + downLoadTask.getId());
    }


    public long addDownLoadTasks(DownLoadTask downLoadTask) {

        return briteDatabase.insert(LJDb.DownLoadTaskTable.TABLE_NAME, LJDb.DownLoadTaskTable.toContentValues(downLoadTask));
    }

    public int deleteDownLoadTaskByFilePath(final String filePath) {
        return briteDatabase.delete(LJDb.DownLoadTaskTable.TABLE_NAME, LJDb.DownLoadTaskTable.COLUME_FILEPATH + "=?", filePath);
    }

    public int deleteDownLoadTaskByID(final int id) {
        return briteDatabase.delete(LJDb.DownLoadTaskTable.TABLE_NAME, LJDb.DownLoadTaskTable.COLUMN_ID + "=?", String.valueOf(id));
    }

    public void close() {
        if (briteDatabase != null) {
            briteDatabase.close();
        }
    }

}
