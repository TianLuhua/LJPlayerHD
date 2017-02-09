package com.example.lj.ljplayerhd.main.offline.download.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.offline.download.DownLoadFragmengPresenter;
import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class DownLoadTaskAdapter extends RecyclerView.Adapter<DownLoadTaskAdapter.ViewHold> {


    private Map<String, DownLoadTask> downLoadTasks;
    private Context mContext;
    private DownLoadFragmengPresenter presenter;


    public DownLoadTaskAdapter(Map<String, DownLoadTask> downLoadTasks, DownLoadFragmengPresenter presenter) {
        this.downLoadTasks = downLoadTasks;
        this.presenter = presenter;
    }


    public Map<String, DownLoadTask> getDownLoadTasks() {
        return downLoadTasks;
    }

    public void setDownLoadTasks(Map<String, DownLoadTask> downLoadTasks) {
        this.downLoadTasks = downLoadTasks;
    }


    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHold hold = null;
        if (hold == null) {
            mContext = parent.getContext();
            hold = new ViewHold(LayoutInflater.from(mContext).inflate(R.layout.fragment_persoanl_download_recycle_item, parent, false));
        }
        return hold;
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        int i = -1;
        String key = "";
        DownLoadTask task = null;

        Set<String> keys = downLoadTasks.keySet();
        if (keys != null) {
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                i++;
                key = (String) iterator.next();
                if (i == position) {
                    SLogUtil.e("info", "onBindViewHolder---position：" + position);
                    task = downLoadTasks.get(key);
                    break;
                }
            }
        }
        SLogUtil.e("tlh", "77777777777:" + System.nanoTime());
        if (task != null) {
            Glide.with(mContext).load(IDatas.WebService.THUMB_PATH + task.getPicaddr()).asBitmap().into(holder.imageView);
            holder.progressBar.setProgress(task.getDownloadProgress());
            holder.title.setText(task.getName().toString());
            StringBuilder builder = new StringBuilder();
            if (builder != null) {
                builder.append("文件大小：" + android.text.format.Formatter.formatFileSize(mContext, task.getAllCount()));
                builder.append("\n");
                builder.append(task.getTineLen());
                builder.append("\n");
                builder.append("下载进度：" + task.getDownloadProgress() + "%");
            }
            holder.totlaSize.setText(builder.toString());
            holder.download_item_root.setTag(task);
        }
    }

    @Override
    public int getItemCount() {
        return downLoadTasks != null ? downLoadTasks.size() : 0;
    }

    public class ViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout download_item_root;
        private ImageView imageView;
        private TextView title;
        private TextView totlaSize;
        private ProgressBar progressBar;

        public ViewHold(View itemView) {
            super(itemView);
            download_item_root = (LinearLayout) itemView.findViewById(R.id.download_item_root);
            imageView = (ImageView) itemView.findViewById(R.id.download_item_ioc);
            totlaSize = (TextView) itemView.findViewById(R.id.local_item_progress_text);
            title = (TextView) itemView.findViewById(R.id.download_item_name);
            progressBar = (ProgressBar) itemView.findViewById(R.id.local_item_progress);
            download_item_root.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            DownLoadTask task = (DownLoadTask) view.getTag();
            if (presenter != null)
                presenter.onItemClick(task);
        }
    }


}
