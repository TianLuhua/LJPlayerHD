package com.example.lj.ljplayerhd.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @Description 控制视频封面比例的ImageView
 */
public class View_VideoImageView extends ImageView {

    public View_VideoImageView(Context context, AttributeSet attrs,
	    int defStyleAttr) {
	super(context, attrs, defStyleAttr);
    }

    public View_VideoImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public View_VideoImageView(Context context) {
	super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	if (getWidth() != 0) {
	    super.setMeasuredDimension(getWidth(), 25 * getWidth() / 18);
	} else {
	    int width = MeasureSpec.getSize(widthMeasureSpec);
	    super.setMeasuredDimension(width, 25 * width / 18);
	}
    }

}
