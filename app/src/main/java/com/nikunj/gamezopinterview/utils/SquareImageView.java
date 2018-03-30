package com.nikunj.gamezopinterview.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by nikunj on 3/30/18.
 */
public class SquareImageView extends android.support.v7.widget.AppCompatImageView
{
    public SquareImageView(Context context)
    {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}