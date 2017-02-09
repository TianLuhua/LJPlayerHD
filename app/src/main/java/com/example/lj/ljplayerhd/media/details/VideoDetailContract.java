package com.example.lj.ljplayerhd.media.details;

import com.example.lj.ljplayerhd.adapter.VideoItemClickListener;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.bean.SearchBean;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.List;

/**
 * Created by wh on 2016/12/7.
 */

public class VideoDetailContract {
    public interface View {
        void setPresenter(Presenter presenter);

        FragmentLifecycleProvider getLifecycleProvider();

        void updateHeadData(SearchBean bean);

        void updateRecommendData(List<PageBean.VideoContentBean> datas);

        void back();

        void goCommentActivity(String mark, String id,String name);

        void showDefinitionDialog(List<DefinitionBean> data,int requestCode);

        void goVideoDetailsActivity(String name);
    }

    public interface Presenter extends VideoItemClickListener{
        void loadDetailsData(String name);

        void loadRecommendData();

        void download(SearchBean bean);

        void comment(SearchBean bean);

        void back();

        void play(SearchBean bean);

        void download(String url);
    }
}
