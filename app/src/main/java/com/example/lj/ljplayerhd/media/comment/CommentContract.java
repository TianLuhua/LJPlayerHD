package com.example.lj.ljplayerhd.media.comment;

import com.example.lj.ljplayerhd.bean.CommentBean;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.List;

/**
 * Created by wh on 2016/12/28.
 */

public class CommentContract {

    public interface View {
        void setPresenter(Presenter presenter);

        FragmentLifecycleProvider getLifecycleProvider();

        void updateComment(List<CommentBean.PagecontentBean> data);

        void back();

        void setOver(boolean b);

        void onLoadFinish();
    }

    public interface Presenter {
        
        void loadCommentData(String mark,String vId);

        void back();

        void loadNextPage();
    }
}
