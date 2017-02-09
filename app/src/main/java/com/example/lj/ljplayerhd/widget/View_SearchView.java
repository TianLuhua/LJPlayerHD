package com.example.lj.ljplayerhd.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.lj.ljplayerhd.R;

import java.lang.reflect.Field;


/**
 * Created by wh on 2016/11/28.
 */

public class View_SearchView extends SearchView implements SearchView.OnQueryTextListener
        , AdapterView.OnItemClickListener {

    private ImageView searchHintIcon;
    private View searchPlate;
    private ImageView closeButton;
    //    private SearchHistoryPopuWindow mPopuWindow;
    private AutoCompleteTextView mAutoCompleteTextView;


    public View_SearchView(Context context) {
        super(context);
        updateStyle();
        init(context);
    }

    public View_SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateStyle();
        init(context);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        if (TextUtils.isEmpty(newText) && mPopuWindow != null)
//            mPopuWindow.dismiss();
//        else if (!TextUtils.isEmpty(newText) && mPopuWindow != null) {
//            if (mAdapter != null && mAdapter.getCount() > 0)
//                mPopuWindow.show(this);
//        }
        if (mQueryTextListener != null)
            return mQueryTextListener.onQueryTextChange(newText);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (mHistoryListener != null)
            mHistoryListener.onHistoryClick(position);
//        if (mPopuWindow != null)
//            mPopuWindow.dismiss();
    }

    private BaseAdapter mAdapter;

    public void setHistoryAdapter(BaseAdapter adapter) {
//        if (adapter != null) {
//            mPopuWindow = new SearchHistoryPopuWindow(getContext());
//            mPopuWindow.setOnHistoryItemClickListener(this);
//            mPopuWindow.setOnClearClickListener(clearClickListener);
//            mPopuWindow.setAdapter(adapter);
//            this.mAdapter = adapter;
//        }
    }

    private OnClickListener clearClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mHistoryListener != null)
                mHistoryListener.onClearClick();
//            if (mPopuWindow != null)
//                mPopuWindow.dismiss();
        }
    };

    public void setKeyListener(KeyListener l) {
//        if (mAutoCompleteTextView != null)
//            mAutoCompleteTextView.setKeyListener(l);
    }

    private OnQueryTextListener mQueryTextListener;

    @Override
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        this.mQueryTextListener = listener;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        if (mPopuWindow != null)
//            mPopuWindow.dismiss();
        if (mQueryTextListener != null)
            return mQueryTextListener.onQueryTextSubmit(query);
        return false;
    }

    private void updateStyle() {
        try {
            Class<?> argClass = this.getClass().getSuperclass();
            // 默认搜索图标
            Field mSearchHintIconField = argClass
                    .getDeclaredField("mSearchHintIcon");
            mSearchHintIconField.setAccessible(true);
            searchHintIcon = (ImageView) mSearchHintIconField.get(this);

            // 下划线
            Field mPlateField = argClass.getDeclaredField("mSearchPlate");
            mPlateField.setAccessible(true);
            searchPlate = (View) mPlateField.get(this);

            // 清除图标
            Field ownField = argClass.getDeclaredField("mCloseButton");
            ownField.setAccessible(true);
            closeButton = (ImageView) ownField.get(this);

            //输入框
            Field QueryField = argClass.getDeclaredField("mQueryTextView");
            QueryField.setAccessible(true);
            mAutoCompleteTextView = (AutoCompleteTextView) QueryField.get(this);
        } catch (Exception e) {
            if (e != null)
                e.printStackTrace();
        }
    }

    public void setHintIconImageResource(int resId) {
        if (searchHintIcon != null)
            searchHintIcon.setImageResource(resId);
    }

    public void setHintIconImageBitmap(Bitmap bm) {
        if (searchHintIcon != null)
            searchHintIcon.setImageBitmap(bm);
    }

    public void setCloseButtonImageBitmap(Bitmap bm) {
        if (closeButton != null)
            closeButton.setImageBitmap(bm);
    }

    public void setCloseButtonImageDrawable(Drawable drawable) {
        if (closeButton != null)
            closeButton.setImageDrawable(drawable);
    }

    public void setCloseButtonImageResource(int resId) {
        if (closeButton != null)
            closeButton.setImageResource(resId);
    }

    private void init(Context context) {
        super.setOnQueryTextListener(this);
    }

    private OnHistoryListener mHistoryListener;

    public void setOnHistoryListener(OnHistoryListener l) {
        this.mHistoryListener = l;
    }

    public interface OnHistoryListener {

        void onClearClick();

        void onHistoryClick(int position);
    }

    public void setHintIconImageDrawable(Drawable drawable) {
        if (searchHintIcon != null)
            searchHintIcon.setImageDrawable(drawable);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setPlateBackground(Drawable background) {
        if (searchPlate != null)
            searchPlate.setBackground(background);
    }

    public void setPlateBackgroundColor(int color) {
        if (searchPlate != null)
            searchPlate.setBackgroundColor(color);
    }

    public void setPlateBackgroundResource(int resid) {
        if (searchPlate != null)
            searchPlate.setBackgroundResource(resid);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCloseButtonBackground(Drawable background) {
        if (closeButton != null)
            closeButton.setBackground(background);
    }

    public void setCloseButtonBackgroundColor(int color) {
        if (closeButton != null)
            closeButton.setBackgroundColor(color);
    }

    public void setCloseButtonBackgroundResource(int resid) {
        if (closeButton != null)
            closeButton.setBackgroundResource(resid);
    }
}
