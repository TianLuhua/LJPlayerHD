package com.example.lj.ljplayerhd.main.search;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.SearchBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.FragmentSearchResultBinding;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.GSONUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wh on 2016/11/19.
 */

public class ResultFragment extends BaseFragment {

    private SearchRecyclerAdapter mAdapter;
    private String searchUrl;
    private FragmentSearchResultBinding resultBinding;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void initViews() {
        resultBinding = FragmentSearchResultBinding.bind(root);
        resultBinding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchRecyclerAdapter(getActivity());
        resultBinding.recycler.setAdapter(mAdapter);
    }

    @Override
    public void initEvents() {
    }

    public void search(String query) {
        try {
            showProgress();
            searchUrl = IDatas.WebService.SEARCH_VIDEO
                    + "&key=" + URLEncoder.encode(query, "UTF-8");
            final Request<String> request = NoHttp.createStringRequest(searchUrl);
            RxNoHttp.request(this, request, new Action1<Response<String>>() {
                @Override
                public void call(Response<String> response) {
                    if (response.get() == null)
                        return;
                    List<SearchBean> datas = GSONUtils.parseJsonArrayString(response.get());
                    hideProgress();
                    mAdapter.setData(datas);
                }
            });
        } catch (UnsupportedEncodingException e) {
            hideProgress();
            e.printStackTrace();
        }
    }

    private void showProgress() {
        if (resultBinding.progress != null)
            resultBinding.progress.setVisibility(View.VISIBLE);
        if (resultBinding.recycler != null)
            resultBinding.recycler.setVisibility(View.GONE);
    }

    private void hideProgress() {
        if (resultBinding.progress != null)
            resultBinding.progress.setVisibility(View.GONE);
        if (resultBinding.recycler != null)
            resultBinding.recycler.setVisibility(View.VISIBLE);
    }
}
