package com.example.lj.ljplayerhd.media.category;

import com.example.lj.ljplayerhd.bean.CategoryModel;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by wh on 2016/12/13.
 */

public class CategoryContract {

    public interface View {
        void setPresenter(Presenter presenter);

        void back();

        void showOrder();

        void showCategory(boolean isChecked);

        void goSearchActivity();

        void updateData(List<PageBean.VideoContentBean> data);

        FragmentLifecycleProvider getLifecycleProvider();

        void updateCategory(CategoryModel model);

        void onLoadFinish();

        void setOver(boolean b);

        void showDefinitionDialog(List<DefinitionBean> data, int requestCode);

        void goVideoDetailActivity(String name);
    }

    public interface Presenter {

        void back();

        void selectedType();

        void category(boolean isChecked);

        void goSearch();

        void loadFirstPageData(String mark);

        void loadNextPage();

        void onOrderChanged(int id);

        void loadCategoryData();

        void loadCategorySelected(Map<String, String> selected);
    }
}
