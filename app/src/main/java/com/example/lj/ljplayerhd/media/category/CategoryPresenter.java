package com.example.lj.ljplayerhd.media.category;

import android.text.TextUtils;
import android.util.Log;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.adapter.VideoItemClickListener;
import com.example.lj.ljplayerhd.bean.CategoryModel;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.NetUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.functions.Action1;

/**
 * Created by wh on 2016/12/13.
 */

public class CategoryPresenter implements CategoryContract.Presenter, VideoItemClickListener {

    CategoryContract.View mView;
    private List<PageBean.VideoContentBean> pageData;
    private int total;
    private int pageSize;
    private int page = -1;
    private String dataUrl = "";
    private Map<String, String> fields = new HashMap<>();

    public CategoryPresenter(CategoryContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        pageData = new ArrayList<>();
    }

    @Override
    public void back() {
        mView.back();
    }

    @Override
    public void selectedType() {
        mView.showOrder();
    }

    @Override
    public void category(boolean isChecked) {
        mView.showCategory(isChecked);
    }

    @Override
    public void goSearch() {
        mView.goSearchActivity();
    }

    @Override
    public void loadFirstPageData(String mark) {
        fields.put("mark", mark);
        fields.put("order", "time");//默认order
        setDataUrl();//更新url
        loadData();
    }

    public void setDataUrl() {
        dataUrl = IDatas.WebService.VIDEO_LIST_PATH;
        Set entries = fields.entrySet();
        if (entries != null) {
            Iterator iterator = entries.iterator();
            String result = "";
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if ("tag".equals(key)) {
                    result += value;
                } else {
                    result += "&" + key + "=" + value;
                }
            }
            dataUrl += result;
        }
    }

    public String getDataUrl() {
        Log.e("info", ".....dataUrl:" + dataUrl + "&page=" + page);
        return dataUrl + "&page=" + page;
    }

    private void flushData(Response<PageBean> response) {
        pageSize = Integer.parseInt(response.get().getSplitpage().getPagesize());
        total = Integer.parseInt(response.get().getSplitpage().getTotal());
        pageData.addAll(response.get().getPagecontent());
        mView.updateData(pageData);
        page++;
    }

    private void loadData() {
        this.page = 1;
        mView.setOver(false);
        if (pageData != null) {
            pageData.clear();
            mView.updateData(pageData);
        }
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), getDataUrl(), new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                if (response.get() == null)
                    return;
                flushData(response);
            }
        });
    }

    @Override
    public void loadNextPage() {
        if (pageData.size() >= total || page > pageSize) {
            mView.setOver(true);
            return;
        }
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), getDataUrl(), new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                if (response.get() == null)
                    return;
                page++;
                pageData.addAll(response.get().getPagecontent());
                mView.updateData(pageData);
                mView.onLoadFinish();
            }
        });
    }

    @Override
    public void onOrderChanged(int id) {
        String order = "";
        switch (id) {
            case R.id.time:
                order = "time";
                break;
            case R.id.hits:
                order = "hits";
                break;
            case R.id.rating:
                order = "rating";
                break;
            case R.id.effect:
                order = "effect";
                break;
        }
        fields.put("order", order);
        setDataUrl();
        loadData();
    }

    @Override
    public void loadCategoryData() {
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), IDatas.WebService.CATEGORY_PATH, new Action1<Response<CategoryModel>>() {
            @Override
            public void call(Response<CategoryModel> response) {
                if (response.get() != null)
                    mView.updateCategory(response.get());
            }
        });
    }

    @Override
    public void loadCategorySelected(Map<String, String> selected) {
        if (selected == null)
            return;
        Set entries = selected.entrySet();
        if (entries != null) {
            Iterator iterator = entries.iterator();
            String result = "";
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                result += "&" + key + "=" + value;
            }
            fields.put("tag", result);
            setDataUrl();
            loadData();
        }
    }

    @Override
    public void onItemClick(PageBean.VideoContentBean bean) {
        String mark = fields.get("mark");
        if (!TextUtils.isEmpty(mark) && !"video".equals(mark)) {
            List<DefinitionBean> data = NetUtil.getPlayUrl(bean.getAddress());
            mView.showDefinitionDialog(data, IDatas.Messages.REQUEST_PLAY);
        } else {
            mView.goVideoDetailActivity(bean.getName());
        }
    }
}
