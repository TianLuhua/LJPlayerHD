package com.example.lj.ljplayerhd.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.VideoListChildItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/12/9.
 */

public class VideoItemRecyclerAdapter extends RecyclerView.Adapter<VideoItemRecyclerAdapter.ViewHolder> {

    private List<PageBean.VideoContentBean> datas;
    private VideoItemClickListener mListener;
    private String mark;

    public VideoItemRecyclerAdapter(VideoItemClickListener listener) {
        this.datas = new ArrayList<>();
        this.mListener=listener;
    }

    public void setData(List<PageBean.VideoContentBean> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    public void setMark(String mark){
        this.mark=mark;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_child_item, parent, false);
        VideoListChildItemBinding.bind(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PageBean.VideoContentBean bean = datas.get(position);
        if (bean == null)
            return;
        VideoListChildItemBinding itemBinding = DataBindingUtil.getBinding(holder.item);
        if (itemBinding == null)
            return;
        VideoItemClickHandler handler=new VideoItemClickHandler(mListener);
        itemBinding.setBean(bean);
        itemBinding.setHandler(handler);
        itemBinding.executePendingBindings();
        if (!TextUtils.isEmpty(bean.getPicaddr())) {
            Glide.with(itemBinding.img.getContext()).load(IDatas.WebService.THUMB_PATH + bean.getPicaddr())
                    .placeholder(R.drawable.film_cover_loading).error(R.drawable.film_cover_loading).into(itemBinding.img);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View item;

        public ViewHolder(View item) {
            super(item);
            this.item = item;
        }
    }
}
