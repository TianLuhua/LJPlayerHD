package com.example.lj.ljplayerhd.main.firstpager.expandable;

import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.main.firstpager.expandable.adapter.TypeExpandableAdapter;
import com.example.lj.ljplayerhd.main.firstpager.expandable.adapter.TypeListHeadViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wh on 2016/11/26.
 */

public class TypePagerPresenter implements TypePagerContract.Presenter {

    private TypePagerContract.View mView;

    public TypePagerPresenter(TypePagerContract.View view){
        this.mView=view;
    }

    @Override
    public synchronized  void updateData(String order, PageBean pageBean) {
        if(pageBean == null)
            return;
        List<PageBean.VideoContentBean> contents = pageBean.getPagecontent();
        if(contents ==null)
            return;
        Map<String,List<PageBean.VideoContentBean>> data=new HashMap<>();
        Collections.shuffle(contents);
        if(contents.size()> TypeExpandableAdapter.maxItemCount){
            List<PageBean.VideoContentBean> list=new ArrayList<>();
            for (int i = 0; i < TypeExpandableAdapter.maxItemCount ; i++) {
                list.add(contents.get(i));
            }
            data.put(order,list);
        }else {
            data.put(order,contents);
        }
        mView.updateData(data);
    }

    @Override
    public void updateHeadData(PageBean pageBean) {
        if(pageBean == null)
            return;
        List<PageBean.VideoContentBean> contents = pageBean.getPagecontent();
        if(contents ==null)
            return;
        Collections.shuffle(contents);
        if(contents.size()> TypeListHeadViewAdapter.maxCount){
            List<PageBean.VideoContentBean> list=new ArrayList<>();
            for (int i = 0; i < TypeListHeadViewAdapter.maxCount ; i++) {
                list.add(contents.get(i));
            }
            mView.updateHeadView(list);
        }else {
            mView.updateHeadView(contents);
        }
    }
}
