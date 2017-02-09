package com.example.lj.ljplayerhd.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Created by wh on 2016/12/29.
 */

public class SwipeToLoadRecyclerView extends RecyclerView implements OnLoadMoreListener{

    private SwipeToLoadLayout swipeLayout;
    private boolean isOver=false;

    public SwipeToLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        addOnScrollListener(scrollListener);
    }

    public void setSwipeToLoadLayout(SwipeToLoadLayout layout){
        this.swipeLayout=layout;
        layout.setOnLoadMoreListener(this);
    }

    private OnScrollListener scrollListener=new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                    if(swipeLayout!=null)
                        swipeLayout.setLoadingMore(true);
                }
            }
        }
    };

    public void setOver(boolean over) {
        isOver = over;
        if(isOver)
            loadOver();
    }

    public void onLoadFinish(){
        if(swipeLayout!=null)
            swipeLayout.setLoadingMore(false);
    }

    @Override
    public void onLoadMore() {
        if(!isOver)
            loadMore();
        else
            loadOver();
    }

    private void loadMore(){
        if(swipeLayout!=null && loadMoreListener !=null){
            loadMoreListener.onLoadMore();
        }
    }

    private void loadOver(){
        if(swipeLayout!=null && loadMoreListener !=null)
        {
            loadMoreListener.onLoadOver();
            swipeLayout.setLoadingMore(false);
        }
    }

    private OnLoadMoreListener loadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener l){
        this.loadMoreListener=l;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
        void onLoadOver();
    }
}
