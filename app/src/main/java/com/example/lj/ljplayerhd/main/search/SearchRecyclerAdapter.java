package com.example.lj.ljplayerhd.main.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.SearchBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.VideoDetailsViewBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/11/29.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    List<SearchBean> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public SearchRecyclerAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<SearchBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.video_details_view, parent, false);
        VideoDetailsViewBinding.bind(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position) {
        SearchBean bean = mData.get(position);
        if (bean == null)
            return;
        VideoDetailsViewBinding itemBinding= DataBindingUtil.getBinding(mHolder.view);
        if(itemBinding==null)
            return;
        SearchResultItemClickHandler clickHandler=new SearchResultItemClickHandler(mContext);
        itemBinding.setBean(bean);
        itemBinding.setHandler(clickHandler);
        itemBinding.executePendingBindings();
        if (!TextUtils.isEmpty(bean.getPicaddr())) {
            Glide.with(mContext).load(IDatas.WebService.THUMB_PATH + bean.getPicaddr())
                    .placeholder(R.drawable.film_cover_loading).error(R.drawable.film_cover_loading).into(itemBinding.img);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View root) {
            super(root);
            this.view = root;
        }
    }
}
