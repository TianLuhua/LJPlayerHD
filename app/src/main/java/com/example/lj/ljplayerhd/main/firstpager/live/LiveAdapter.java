package com.example.lj.ljplayerhd.main.firstpager.live;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.LiveBean;
import com.example.lj.ljplayerhd.databinding.LiveListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/12/29.
 */

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {

    private List<LiveBean.RoomBean> data;
    private LivePresenter mPresenter;

    public LiveAdapter(LivePresenter presenter){
        this.mPresenter=presenter;
        data=new ArrayList<>();
    }

    public void setData(List<LiveBean.RoomBean> data){
        this.data=data;
        notifyDataSetChanged();
    }

    @Override
    public LiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.live_list_item,parent,false);
        LiveListItemBinding.bind(itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LiveAdapter.ViewHolder holder, int position) {
        LiveBean.RoomBean bean=data.get(position);
        if(bean==null)
            return;
        LiveListItemBinding itemBinding= DataBindingUtil.getBinding(holder.itemView);
        if(itemBinding==null)
            return;
        itemBinding.setPresenter(mPresenter);
        itemBinding.setBean(bean);
        itemBinding.executePendingBindings();
        if(!TextUtils.isEmpty(bean.getCreator().getPortrait())){
            Glide.with(holder.itemView.getContext())
                    .load(bean.getCreator().getPortrait())
                    .placeholder(R.drawable.film_cover_loading)
                    .error(R.drawable.film_cover_loading)
                    .into(itemBinding.img);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
