package com.example.lj.ljplayerhd.main.search;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.media.details.VideoDetailActivity;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.wefika.flowlayout.FlowLayout;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by wh on 2016/11/19.
 */

public class DefaultFragment extends BaseFragment implements View.OnClickListener {

    private FlowLayout flowLayout;
    private LayoutInflater mInflater;
    private String mark = "video";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_defult;
    }

    @Override
    public void initViews() {
        flowLayout = (FlowLayout) root.findViewById(R.id.flow_contain_layout);
        mInflater = LayoutInflater.from(getActivity());
    }

    @Override
    public void initEvents() {
        String url = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + mark + "&order=" + "hits" + "&page=" + "1";
        RxNoHttp.requestJavaBean(this, url, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                PageBean pageBean = response.get();
                if (pageBean != null)
                    initFlow(pageBean.getPagecontent());
            }
        });
    }

    private void initFlow(List<PageBean.VideoContentBean> pageContents) {
        if (pageContents != null)
            for (int i = 0; i < pageContents.size(); i++) {
                View flow = mInflater.inflate(R.layout.flow_item, null);
                TextView text = (TextView) flow.findViewById(R.id.flow_item_text);
                PageBean.VideoContentBean data = pageContents.get(i);
                text.setTag(data);
                text.setText(data.getName() == null ? "" : data.getName());
                FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                        FlowLayout.LayoutParams.WRAP_CONTENT);
                flowLayout.addView(flow, params);
                text.setOnClickListener(this);
            }
    }

    @Override
    public void onClick(View view) {
        PageBean.VideoContentBean data = (PageBean.VideoContentBean) view.getTag();
        if (data == null || TextUtils.isEmpty(data.getName()))
            return;
        getActivity().startActivity(VideoDetailActivity.newIntent(getActivity(), data.getName()));
    }
}
