package com.example.lj.ljplayerhd.main.offline.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.main.offline.download.db.LJDbManager;
import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.utils.NetUtil;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by tlh on 2016/12/13 0013.
 * 下载
 */

public class DownLoadService extends Service {

    public static final String ACTION = "com.example.lj.ljplayerhd.fragment_offline_dlna.offline.download.DownLoadService";
    private final String LJPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LJPlayerHD" + File.separator + "Cache";
    private DownloadQueue downloadQueue;
    private RxBus rxBus;
    private Map<String, DownLoadTask> downLoadTasks;
    private LJDbManager ljDbManager;


    @Override
    public void onCreate() {
        super.onCreate();
        SLogUtil.e("tlh", "DownLoadService:" + "Start");
        if (downloadQueue == null)
            downloadQueue = NoHttp.getDownloadQueueInstance();
        rxBus = RxBus.getInstance();
        downLoadTasks = new HashMap<>();
        ljDbManager = LJApplication.getLJDbManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SLogUtil.e("tlh", "DownLoadService:" + "onStartCommand");
        rxBus.receive(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if (o instanceof DownLoadTask) {
                    SLogUtil.e("tlh", "Func1——url:" + ((DownLoadTask) o).getAddress());
                    if (((DownLoadTask) o).getStatus() == DownLoadTask.DOWNLOCADBEAN_STATUS_NOMAL) {
                        SLogUtil.e("tlh", "2222222222:" + System.nanoTime());
                        return true;
                    }
                    return false;
                }
                return false;
            }
        }, new Action1<Object>() {
            @Override
            public void call(Object o) {
                SLogUtil.e("tlh", "DownLoadTask subscribe!!");
                DownLoadTask task = (DownLoadTask) o;
                if (task.getStatus() == DownLoadTask.DOWNLOCADBEAN_STATUS_NOMAL) {
                    if (download(task, downloadListener)) {
                        downLoadTasks.put(String.valueOf(task.getId()), task);
                        ljDbManager.addDownLoadTasks(task);
                        SLogUtil.e("tlh", "3333333333:" + System.nanoTime());
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                SLogUtil.e("info", "DownLoadService---任务出错！");
            }
        }, new Action0() {
            @Override
            public void call() {
                SLogUtil.e("info", "DownLoadService---完成！");

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }


    private boolean download(DownLoadTask task, DownloadListener downloadListener) {

        DownloadRequest downloadRequest = null;
        // 开始下载了，但是任务没有完成，代表正在下载，那么暂停下载。
        if (downloadRequest != null && downloadRequest.isStarted() && !downloadRequest.isFinished()) {
            // 暂停下载。
//            downloadRequest.cancel();
            //开始下载
//            downloadRequest.start();
            return false;
        } else if (downloadRequest == null) {

            /**
             * 如果使用断点续传的话，一定要指定文件名喔。
             */
            // url 下载地址。
            // fileFolder 保存的文件夹。
            // fileName 文件名。
            // isRange 是否断点续传下载。
            // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
//            Log.e("info", "downLoadBean:" + downLoadBean.toString());
//            NetUtil.getVideoSuffix(task.getSearchBean().getAddress(),task.getSearchBean().getName())
            SLogUtil.e("tlh", "Address():" + task.getAddress() + ";name:" + NetUtil.getVideoSuffix(task.getAddress(), task.getName()));
            downloadRequest = NoHttp.createDownloadRequest(task.getAddress(), LJPATH, NetUtil.getVideoSuffix(task.getAddress(), task.getName()), true, false);
            downloadRequest.setCancelSign(task.getId());
            // what 区分下载。
            // downloadRequest 下载请求对象。
            // downloadListener 下载监听。
            downloadQueue.add(Integer.valueOf(task.getId()), downloadRequest, downloadListener);
            return true;
        }
        return false;
    }


    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {

            SLogUtil.e("info", what + "----onStart:");
            String key = String.valueOf(what);
            DownLoadTask task = downLoadTasks.get(key);
            task.setStatus(DownLoadTask.DOWNLOCADBEAN_STATUS_START);
            task.setId(what);
            task.setBoforLength(beforeLength);
            task.setAllCount(allCount);
            ljDbManager.replaceDownLoadTasks(task);
            SLogUtil.e("tlh", "444444444444444:" + System.nanoTime());
            rxBus.send(1);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            message = String.format(Locale.getDefault(), message, messageContent);
            String key = String.valueOf(what);
            Log.e("info", what + "----onDownloadError:" + message);
            DownLoadTask task = downLoadTasks.get(key);
            task.setStatus(DownLoadTask.DOWNLOCADBEAN_STATUS_ERROR);
            task.setId(what);
            task.setMessage(message);
            ljDbManager.replaceDownLoadTasks(task);
//            rxBus.send(1);

        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            Log.e("info", what + "--progress:" + progress);
            String key = String.valueOf(what);
            DownLoadTask task = downLoadTasks.get(key);
            task.setStatus(DownLoadTask.DOWNLOCADBEAN_STATUS_PROGRESS);
            task.setId(what);
            task.setDownloadProgress(progress);
            task.setFileCount(fileCount);
            ljDbManager.replaceDownLoadTasks(task);
//            rxBus.send(1);

        }

        @Override
        public void onFinish(int what, String filePath) {
            Log.e("tlh", "onFinish:" + what + "--filePath:" + filePath);
            String key = String.valueOf(what);
            DownLoadTask task = downLoadTasks.get(key);
            task.setId(what);
            task.setStatus(DownLoadTask.DOWNLOCADBEAN_STATUS_FINISH);
            task.setFilePath(filePath);
            ljDbManager.replaceDownLoadTasks(task);
//            rxBus.send(1);
        }

        @Override
        public void onCancel(int what) {
            Log.e("info", what + "---onCancel:");
            String key = String.valueOf(what);
            DownLoadTask task = downLoadTasks.get(key);
            task.setStatus(DownLoadTask.DOWNLOCADBEAN_STATUS_CANCEL);
            task.setId(what);
            ljDbManager.replaceDownLoadTasks(task);
//            rxBus.send(1);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        SLogUtil.e("info", "DownLoadService---Destroy!");
        rxBus.unsubscribe();
        ljDbManager.close();
        downloadQueue.cancelAll();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}
