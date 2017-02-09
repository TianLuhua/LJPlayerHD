package com.example.lj.ljplayerhd.main.offline.local.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.LocalVedio;
import com.example.lj.ljplayerhd.main.offline.download.Adapter.DownLoadTaskAdapter;
import com.example.lj.ljplayerhd.main.offline.local.LocalFragmentPresenter;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/24 0024.
 */

public class LocalFragmentAdapter extends RecyclerView.Adapter<LocalFragmentAdapter.ViewHolder> {

    private List<LocalVedio> localVedios;
    private Context mContext;
    private LocalFragmentPresenter mPresenter;

    public LocalFragmentAdapter(List<LocalVedio> localVedios, LocalFragmentPresenter presenter) {
        this.localVedios = localVedios;
        this.mPresenter=presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_persoanl_local_recycler_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(localVedios.get(position).getDisplayName());
        viewHolder.time.setText(localVedios.get(position).getSize());
        Glide.with(mContext).
                load(localVedios.get(position).getThumbPath()).
                asBitmap().
                into(viewHolder.ioc);
        SLogUtil.e("tlh",localVedios.get(position).getThumbPath());
        viewHolder.local_item_root.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT));
        viewHolder.local_item_root.setTag(localVedios.get(position).getPath());
    }

    @Override
    public int getItemCount() {
        return localVedios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ioc;
        private TextView name;
        private TextView time;
        private LinearLayout local_item_root;

        public ViewHolder(View itemView) {
            super(itemView);
            ioc = (ImageView) itemView.findViewById(R.id.local_item_ioc);
            name = (TextView) itemView.findViewById(R.id.local_item_name);
            time = (TextView) itemView.findViewById(R.id.local_item_time);
            local_item_root = (LinearLayout) itemView.findViewById(R.id.local_item_root);
            local_item_root.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String path= (String) view.getTag();
            if(mPresenter!=null)
                mPresenter.onItemClick(path);
        }
    }

}
