package com.example.lj.ljplayerhd.main.firstpager.expandable.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.media.details.VideoDetailActivity;
import com.example.lj.ljplayerhd.widget.View_VideoImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wh on 2016/11/26.
 */

public class TypeExpandableAdapter extends BaseExpandableListAdapter {

    private Map<String, List<PageBean.VideoContentBean>> mData;
    private List<String> mKey;
    private LayoutInflater mInflater;
    private ExpandableListView mListView;
    private int numColumns = 3;
    public static int maxItemCount = 6;

    public TypeExpandableAdapter(ExpandableListView listView) {
        this.mListView = listView;
        mInflater = LayoutInflater.from(listView.getContext());
        mData = new HashMap<String, List<PageBean.VideoContentBean>>();
        mKey = new ArrayList<>();
    }

    public void setData(Map<String, List<PageBean.VideoContentBean>> data) {
        this.mData = data;
        updateKey();
        notifyDataSetChanged();
    }

    private void updateKey() {
        mKey.clear();
        for (Map.Entry entry : mData.entrySet()) {
            Object key = entry.getKey();
            mKey.add(key.toString());
        }
    }

    public void addData(Map<String, List<PageBean.VideoContentBean>> data) {
        mData.putAll(data);
        updateKey();
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Object key = mKey.get(groupPosition);
        List<PageBean.VideoContentBean> list = mData.get(key);
        if (list != null)
            return (list.size() % numColumns > 0 ? list.size() / numColumns
                    + 1 : list.size() / numColumns);
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.video_list_group_item, null);
            holder = new GroupViewHolder(convertView);
        } else
            holder = (GroupViewHolder) convertView.getTag();
        if (holder == null)
            holder = new GroupViewHolder(convertView);
        if (!isExpanded)
            mListView.expandGroup(groupPosition);
        String key = mKey.get(groupPosition);
        if (IDatas.JsonKey.ORDER_TIME.equals(key)) {
            holder.type.setText(R.string.recent_update);
        } else if (IDatas.JsonKey.ORDER_HITS.equals(key)) {
            holder.type.setText(R.string.recent_hit);
        } else if (IDatas.JsonKey.ORDER_RATING.equals(key)) {
            holder.type.setText(R.string.top_rated);
        } else if (IDatas.JsonKey.ORDER_EFFECT.equals(key)) {
            holder.type.setText(R.string.new_films);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.video_list_child_threeitem, null);
            holder = new ChildViewHolder(convertView);
        } else
            holder = (ChildViewHolder) convertView.getTag();
        if (holder == null)
            holder = new ChildViewHolder(convertView);
        String key = mKey.get(groupPosition);
        List<PageBean.VideoContentBean> list = mData.get(key);
        if (list == null)
            return convertView;
        int begin = childPosition * numColumns;
        //(begin + 3)代表接下来要循环三次获取集合数据begin+i
        int size = (begin + numColumns) > list.size() ? (list.size() - begin)  //检测超过数据大小,设置隐藏
                : numColumns;
        for (int i = 0; i < numColumns; i++) {
            setViewData(list, holder, begin + i, i < size);
        }
        return convertView;
    }

    private void setViewData(List<PageBean.VideoContentBean> list, ChildViewHolder holder, int position, boolean isVisible) {
        int mod = position % numColumns;
        ChildViewHolder.ItemHolder mHolder = null;
        switch (mod) {
            case 0:
                mHolder = holder.leftHolder;
                break;
            case 1:
                mHolder = holder.centerHolder;
                break;
            case 2:
                mHolder = holder.rightHolder;
                break;
        }
        if (isVisible) {
            mHolder.item.setVisibility(View.VISIBLE);
            mHolder.item.setEnabled(true);
            PageBean.VideoContentBean bean = list.get(position);
            if (bean == null)
                return;
            Log.e("info", "getName............" + bean.getName());
            mHolder.name.setText(bean.getName() == null ? "" : bean.getName());
            mHolder.detail.setText(bean.getDetails() == null ? "" : bean.getDetails());
            mHolder.score.setText(bean.getScore() == null ? "" : bean.getScore());
            mHolder.time.setText(bean.getTimeall() == null ? "" : bean.getTimeall());
            if (!TextUtils.isEmpty(bean.getPicaddr())) {
                Glide.with(mListView.getContext()).load(IDatas.WebService.THUMB_PATH + bean.getPicaddr())
                        .placeholder(R.drawable.film_cover_loading).error(R.drawable.film_cover_loading).into(mHolder.img);
            }
            mHolder.img.setOnClickListener(new MOnClickListener(bean) {
                @Override
                public void onClick(View view) {
                    if(itemClickListener!=null)
                        itemClickListener.onItemClick(this.bean);
                }
            });
        } else {
            mHolder.item.setVisibility(View.INVISIBLE);
            mHolder.item.setEnabled(false);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener l){
        itemClickListener=l;
    }

    public class GroupViewHolder {
        public TextView type;

        public GroupViewHolder(View root) {
            root.setTag(this);
            type = (TextView) root.findViewById(R.id.video_list_group_item_type);
        }
    }

    public class ChildViewHolder {
        public ItemHolder leftHolder;
        public ItemHolder centerHolder;
        public ItemHolder rightHolder;

        public ChildViewHolder(View root) {
            root.setTag(this);
            View left = root.findViewById(R.id.video_list_child_threeitem_left);
            View center = root.findViewById(R.id.video_list_child_threeitem_center);
            View right = root.findViewById(R.id.video_list_child_threeitem_right);
            leftHolder = new ItemHolder(left);
            centerHolder = new ItemHolder(center);
            rightHolder = new ItemHolder(right);
        }

        public class ItemHolder {
            public View item;
            public View_VideoImageView img;
            public TextView name;
            public TextView detail;
            public TextView time;
            public TextView score;

            public ItemHolder(View root) {
                this.item = root;
                img = (View_VideoImageView) root.findViewById(R.id.img);
                name = (TextView) root.findViewById(R.id.video_list_child_item_name);
                detail = (TextView) root.findViewById(R.id.video_list_child_item_detail);
                time = (TextView) root.findViewById(R.id.video_list_child_item_time);
                score = (TextView) root.findViewById(R.id.video_list_child_item_score);
            }
        }

    }
}
