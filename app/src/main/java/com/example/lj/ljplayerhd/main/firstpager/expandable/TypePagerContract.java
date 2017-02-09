package com.example.lj.ljplayerhd.main.firstpager.expandable;

import com.example.lj.ljplayerhd.bean.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by wh on 2016/11/26.
 */

public class TypePagerContract {
    
    public interface View{

        void updateData(Map<String, List<PageBean.VideoContentBean>> data);

        void updateHeadView(List<PageBean.VideoContentBean> contents);
    }
    
    public interface Presenter{

        void updateData(String orderTime, PageBean pageBean);

        void updateHeadData(PageBean pageBean);
    }
}
