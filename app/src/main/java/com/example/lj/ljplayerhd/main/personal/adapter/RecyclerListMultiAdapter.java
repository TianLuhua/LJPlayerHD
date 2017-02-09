package com.example.lj.ljplayerhd.main.personal.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.utils.SLogUtil;

import java.util.List;

/**
 * Created by andy.shangguan on 2016/11/30.
 */

public class RecyclerListMultiAdapter extends RecyclerView.Adapter<RecyclerListMultiAdapter.TextViewHolder> {

    private String[] titles;
    private int[] iocs;
    private Context mContext;

    public RecyclerListMultiAdapter(String[] titles, int[] iocs) {
        this.titles = titles;
        this.iocs = iocs;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_persoanl_center_recycler_item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.mTextView.setText(titles[position]);
        SLogUtil.e("info", "----------:" + iocs.length);
//        holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(iocs[position], 0, 0, 0);
        holder.mTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(iocs[position], 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.length;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public TextViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.personalfragment_system_settings);
        }
    }
}
