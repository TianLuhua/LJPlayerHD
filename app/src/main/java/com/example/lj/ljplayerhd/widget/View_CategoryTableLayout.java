package com.example.lj.ljplayerhd.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.CategoryModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wh on 2016/12/26.
 */

public class View_CategoryTableLayout extends TableLayout implements View_CategoryScrollView.OnCategoryItemClickListener{

    private CategoryModel model;
    private Map<String, String> selected;
    private View_CategoryScrollView[] categoryScrollViews;

    public View_CategoryTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        selected = new HashMap<>();
    }

    public void setData(CategoryModel model) {
        this.model = model;
        createView();
    }

    private void createView() {
        if (model == null)
            return;
        categoryScrollViews = new View_CategoryScrollView[model.getCategorys().size()];
        for (int i = 0; i < categoryScrollViews.length; i++) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setOrientation(TableRow.HORIZONTAL);
            tableRow.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            tableRow.addView(linearLayout, new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(getContext());
            textView.setTextSize(12);
            textView.setTextColor(Color.BLUE);
            textView.setText(model.getCategoryLabels()[i]);
            linearLayout.addView(textView, params);
            categoryScrollViews[i] = new View_CategoryScrollView(getContext());
            categoryScrollViews[i].setDatas(model.getCategorys(),model.getCategoryKeys().get(i));
            categoryScrollViews[i].setOnCategoryItemClickListener(this);
            linearLayout.addView(categoryScrollViews[i], params);
            addView(tableRow);
        }
    }

    private OnCategoryClickListener categoryClickListener;

    public void setOnCategoryClickListener(OnCategoryClickListener l) {
        this.categoryClickListener = l;
    }

    @Override
    public void onCategoryItemClick(String key, String selectedItem) {
        selected.put(key,selectedItem);
        if(this.categoryClickListener!=null)
            categoryClickListener.onCategoryClick(selected);
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Map<String, String> selected);
    }
}
