package com.mint.ximalaya.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mint.ximalaya.R;

public class LoadView extends android.support.v7.widget.AppCompatImageView {
    int rotateDegree = 0;
    boolean flagrotate = false;
    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.drawable.loadingpage);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(new Runnable() {
            @Override
            public void run() {
                flagrotate = true;
                rotateDegree += 10;
                rotateDegree %= 360;
                invalidate();
                if (flagrotate){
                    postDelayed(this, 100);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        flagrotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(rotateDegree, getWidth()/2, getHeight()/2);
        super.onDraw(canvas);
    }
}
