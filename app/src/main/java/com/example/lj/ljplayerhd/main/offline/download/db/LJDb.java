package com.example.lj.ljplayerhd.main.offline.download.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;

import rx.functions.Func1;

/**
 * Created by tianluhua on 2017/1/10 0010.
 * /**
 * id : 207
 * mark : video
 * name : 新天师斗僵尸
 * picaddr : day_120412/F/density_55C6DD.jpg
 * year : 2011
 * director : 克雷格·吉勒斯佩
 * playactor : 安东·尤金,伊莫琴·普茨,柯林·法瑞尔,克里斯托夫·梅兹-普莱瑟
 * area : 欧美
 * type : 喜剧 恐怖
 * track : en
 * caption : 5C74A94B.srt
 * captionen :
 * timelen : 6364
 * address : /vod/207/207_480PH.mp4|/vod/207/207_480P.mp4|NULL
 * score : 8.0
 * snum : 0
 */

public final class LJDb {

    private LJDb() {
        throw new RuntimeException("LJDb这个类没有实例！");
    }

    public static abstract class DownLoadTaskTable {
        // 表名
        public static final String TABLE_NAME = "downLoadTasks";

        // 表字段
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_FILEFOLDER = "fileFolder";
        public static final String COLUMN_NAME = "filename";
        public static final String COLUMN_NMESSAGE = "message";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_DOWNLOADPROGRESS = "downloadProgress";
        public static final String COLUME_ALLCOUNT = "allCount";
        public static final String COLUME_BRFORELENGTH = "beforeLength";
        public static final String COLUME_FILECOUNT = "fileCount";
        public static final String COLUME_PICADDR = "picaddr";
        public static final String COLUME_TIMELEN = "timelen";
        public static final String COLUME_FILEPATH = "filePath";


        // 建表语句
        public static final String CREATE_DOWNLOADTABLE =
                "CREATE TABLE "
                        + TABLE_NAME
                        + " ("
                        + COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + COLUMN_ADDRESS + " TEXT,"
                        + COLUMN_FILEFOLDER + " TEXT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_NMESSAGE + " TEXT,"
                        + COLUMN_STATUS + " TEXT,"
                        + COLUMN_DOWNLOADPROGRESS + " TEXT,"
                        + COLUME_ALLCOUNT + " LONG,"
                        + COLUME_BRFORELENGTH + " LONG,"
                        + COLUME_FILECOUNT + " LONG,"
                        + COLUME_PICADDR + " TEXT,"
                        + COLUME_TIMELEN + " TEXT,"
                        + COLUME_FILEPATH + " TEXT"
                        + " ); ";

        // 对象转字段,放入表中
        public static ContentValues toContentValues(DownLoadTask downLoadTask) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, downLoadTask.getId());
            values.put(COLUMN_ADDRESS, downLoadTask.getAddress());
            values.put(COLUMN_FILEFOLDER, downLoadTask.getFileFolder());
            values.put(COLUMN_NAME, downLoadTask.getName());
            values.put(COLUMN_STATUS, downLoadTask.getStatus());
            values.put(COLUMN_DOWNLOADPROGRESS, downLoadTask.getDownloadProgress());
            values.put(COLUME_ALLCOUNT, downLoadTask.getAllCount());
            values.put(COLUME_BRFORELENGTH, downLoadTask.getBoforLength());
            values.put(COLUME_FILECOUNT, downLoadTask.getFileCount());
            values.put(COLUME_PICADDR, downLoadTask.getPicaddr());
            values.put(COLUME_TIMELEN, downLoadTask.getTineLen());
            values.put(COLUME_FILEPATH, downLoadTask.getFilePath());
            return values;
        }

        // 响应式的查询,根据表中的row生成一个对象
        static Func1<Cursor, DownLoadTask> PERSON_MAPPER = new Func1<Cursor, DownLoadTask>() {
            @Override
            public DownLoadTask call(Cursor cursor) {
                DownLoadTask downLoadTask = new DownLoadTask();
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                downLoadTask.setId(id);
                String addRess = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                downLoadTask.setAddress(addRess);
                String fileFolder = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILEFOLDER));
                downLoadTask.setFileFolder(fileFolder);
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                downLoadTask.setName(name);
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS));
                downLoadTask.setStatus(status);
                int downLadProgress = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOADPROGRESS));
                downLoadTask.setDownloadProgress(downLadProgress);
                long allCount = cursor.getLong(cursor.getColumnIndexOrThrow(COLUME_ALLCOUNT));
                downLoadTask.setAllCount(allCount);
                long beforeLength = cursor.getLong(cursor.getColumnIndexOrThrow(COLUME_BRFORELENGTH));
                downLoadTask.setBoforLength(beforeLength);
                long fileCount = cursor.getLong(cursor.getColumnIndexOrThrow(COLUME_FILECOUNT));
                downLoadTask.setFileCount(fileCount);
                String picaddr = cursor.getString(cursor.getColumnIndexOrThrow(COLUME_PICADDR));
                downLoadTask.setPicaddr(picaddr);
                String timelen = cursor.getString(cursor.getColumnIndexOrThrow(COLUME_TIMELEN));
                downLoadTask.setTineLen(timelen);
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUME_FILEPATH));
                downLoadTask.setFilePath(filePath);
                return downLoadTask;
            }
        };

    }

}
