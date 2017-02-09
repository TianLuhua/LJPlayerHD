package com.example.lj.ljplayerhd.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.CategoryModel;
import com.example.lj.ljplayerhd.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qing
 * @Description 分类选择ScrollView
 * @date 2013-12-5
 */
public class View_CategoryScrollView extends HorizontalScrollView implements
        OnClickListener {

    private LayoutInflater inflater;

    public View_CategoryScrollView(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public View_CategoryScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public View_CategoryScrollView(Context context) {
        super(context);
        init();
    }

    private String key;

    public void setDatas(Map<String, Object> categorys, String key) {
        this.key = key;
        titles = new ArrayList<>();
        Object obj = categorys.get(key);
        if (obj != null)
            if (obj instanceof CategoryModel.CategoryBean) {
                CategoryModel.CategoryBean categoryBean = (CategoryModel.CategoryBean) obj;
                if (categoryBean.getItem() != null)
                    for (int i = 0; i < categoryBean.getItem().size(); i++) {
                        if (categoryBean.getItem().get(i) != null)
                            titles.add(categoryBean.getItem().get(i).getText());
                    }
            } else if (obj instanceof CategoryModel.ClassBean) {
                CategoryModel.ClassBean classBean = (CategoryModel.ClassBean) obj;
                if (classBean.getItem() != null)
                    for (int i = 0; i < classBean.getItem().size(); i++) {
                        if (classBean.getItem().get(i) != null)
                            titles.add(classBean.getItem().get(i).getText());
                    }
            } else if (obj instanceof CategoryModel.TimeBean) {
                CategoryModel.TimeBean timeBean = (CategoryModel.TimeBean) obj;
                if (timeBean.getItem() != null)
                    for (int i = 0; i < timeBean.getItem().size(); i++) {
                        if (timeBean.getItem().get(i) != null)
                            titles.add(timeBean.getItem().get(i).getText());
                    }
            }
        addView();
    }

    private LinearLayout linearlayout;
    private int currentClickIndex;

    public int getCurrentClickIndex() {
        return currentClickIndex;
    }

    public void setCurrentClickIndex(int currentClickIndex) {
        View v = texts[this.currentClickIndex];
        v.setEnabled(true);
        this.currentClickIndex = currentClickIndex;
        v = texts[this.currentClickIndex];
        v.setEnabled(false);
    }

    private void init() {
        linearlayout = new LinearLayout(getContext());
        linearlayout.setOrientation(LinearLayout.HORIZONTAL);
        linearlayout.setGravity(Gravity.CENTER_VERTICAL);
        addView(linearlayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        inflater = LayoutInflater.from(getContext());
    }

    private TextView[] texts;
    private List<String> titles;

    private void addView() {
        if (titles != null) {
            linearlayout.removeAllViews();
            texts = new TextView[titles.size()];
            for (int i = 0; i < titles.size(); i++) {
                TextView textView = (TextView) inflater.inflate(
                        R.layout.category_item, null);
                if (i == 0) {
                    textView.setPadding(0, textView.getPaddingTop(),
                            textView.getPaddingRight(),
                            textView.getPaddingBottom());
                }
                textView.setText(titles.get(i));
                texts[i] = textView;
                textView.setTag(i);
                textView.setOnClickListener(this);
                if (i == currentClickIndex) {
                    textView.setEnabled(false);
                }
                linearlayout.addView(textView);
                if (i != titles.size() - 1) {
                    TextView divText = new TextView(getContext());
                    divText.setText("|");
                    divText.setTextColor(getResources().getColor(
                            R.color.category_default_textColor));
                    linearlayout.addView(divText);
                }
            }
        }
    }

    public void setChildNextFocusUpId(int id) {
        for (TextView view : texts) {
            view.setNextFocusUpId(id);
        }
    }

    public void setChildNextFocusDownId(int id) {
        for (TextView view : texts) {
            view.setNextFocusDownId(id);
        }
    }

    public View getFirstText() {
        return texts[0];
    }

    private OnCategoryItemClickListener mItemClickListener;

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener l) {
        this.mItemClickListener = l;
    }

    public interface OnCategoryItemClickListener {
        void onCategoryItemClick(String key, String selectedItem);
    }


    @Override
    public void onClick(View v) {
        int index = (Integer) v.getTag();
        if (index >= 0 && index < titles.size()) {
            setCurrentClickIndex(index);
            if (mItemClickListener != null)
                mItemClickListener.onCategoryItemClick(key,""+currentClickIndex);
        }
    }

}
