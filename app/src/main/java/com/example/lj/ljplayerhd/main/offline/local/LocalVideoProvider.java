package com.example.lj.ljplayerhd.main.offline.local;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.lj.ljplayerhd.bean.LocalVedio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tianluhua on 2016/11/23.
 */

public class LocalVideoProvider implements ILocalVideoProvider {

    private Context context;

    public LocalVideoProvider(Context context) {
        this.context = context;

    }

    @Override
    public List<LocalVedio> getList() {
        List sysVideoList = new ArrayList<LocalVedio>();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};

        // MediaStore.Video.Media.DATA：视频文件路径；
        // MediaStore.Video.Media.DISPLAY_NAME : 视频文件名，如 testVideo.mp4
        // MediaStore.Video.Media.TITLE: 视频标题 : testVideo
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DISPLAY_NAME};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);

        if (cursor == null) {
            Toast.makeText(context, "没有找到可播放视频文件", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (cursor.moveToFirst()) {
            do {
                LocalVedio info = new LocalVedio();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = context.getContentResolver().query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    info.setThumbPath(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                info.setPath(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                info.setTitle(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));

                info.setDisplayName(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                Log.e("info", "DisplayName:" + info.getDisplayName());
                info.setMimeType(cursor
                        .getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));
//
//                info.setSize(cursor
//                        .getString(cursor
//                                .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                Log.e("tlh", info.toString());
                sysVideoList.add(info);

            } while (cursor.moveToNext());
        }
        return sysVideoList;
    }
}

