package com.example.lj.ljplayerhd.media.comment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.CommentBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.CommentListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/12/28.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<CommentBean.PagecontentBean> data;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context){
        this.mContext=context;
        mInflater=LayoutInflater.from(context);
        data=new ArrayList<>();
    }

    public void setData(List<CommentBean.PagecontentBean> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public void addData(List<CommentBean.PagecontentBean> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.comment_list_item,parent,false);
        CommentListItemBinding.bind(itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        CommentBean.PagecontentBean bean=data.get(position);
        if(bean==null)
            return;
        CommentListItemBinding itemBinding= DataBindingUtil.getBinding(holder.itemView);
        if(itemBinding==null)
            return;
        itemBinding.setBean(bean);
        itemBinding.executePendingBindings();
        if(!TextUtils.isEmpty(bean.getUid())) {
            String url = IDatas.WebService.AVATAR + "uid="
                    + bean.getUid() + "&type=real&size=small";
            Glide.with(mContext).load(url).placeholder(R.drawable.film_cover_loading)
                    .error(R.drawable.film_cover_loading).into(itemBinding.commentImg);
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
