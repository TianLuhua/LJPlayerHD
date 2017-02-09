package com.example.lj.ljplayerhd.main.offline.dlna.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;


public abstract class MBaseAdapter extends BaseAdapter implements
        OnClickListener {

    private LayoutInflater mInflater;

    MBaseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public Object getItem(int position) {
        return getItemData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.device_list_item, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        convertView.setId(position);
        onBindViewHolder(holder, position);
        return convertView;
    }

    public abstract void onBindViewHolder(ViewHolder holder, int position);

    public abstract Object getItemData(int position);

    public abstract int getItemCount();

    public class ViewHolder {
        public ImageView img;
        public TextView txt;
        public View root;

        public ViewHolder(View root) {
            root.setTag(this);
            this.root = root;
            img = (ImageView) root.findViewById(R.id.device_item_img);
            txt = (TextView) root.findViewById(R.id.device_item_txt);
            root.setOnClickListener(MBaseAdapter.this);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onItemClick(v, v.getId());
    }
}
