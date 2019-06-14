package com.rg.rgview.jingYunEffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DiffuseView extends View {
    private static final String TAG = "DiffuseView";

    private int mMaxRadiusWidth;
    private int mMinRadiusWidth;

    private boolean mIsDiffuse = false;

    private List<Integer> mAlphas = new ArrayList<>();

    private List<Integer> mWidths = new ArrayList<>();

    private Paint mPaint;

    //扩散圆宽度
    private int mDiffuseWidth = 35;

    public DiffuseView(Context context) {
        this(context, null);
    }

    public DiffuseView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DiffuseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        float toDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110,
                this.getResources().getDisplayMetrics());
        mMinRadiusWidth = (int) toDp;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMaxRadiusWidth = Math.min(getMeasuredHeight() / 2, getMeasuredWidth() / 2);
        Log.d(TAG, "this is " + mMaxRadiusWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.GRAY);
        if (mWidths.isEmpty()) {
            mWidths.add(mMinRadiusWidth);
            mAlphas.add(255);
        }
        for (int i = 0; i < mAlphas.size(); i++) {
            Integer alpha = mAlphas.get(i);
            mPaint.setAlpha(alpha);
            Integer width = mWidths.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, width, mPaint);

            if (alpha >= 2 && width <= mMaxRadiusWidth) {
                mAlphas.set(i, alpha - 2);
                mWidths.set(i, width + 1);
            } else if (width <= mMinRadiusWidth) {
                mWidths.set(i, width + 1);
            }
        }
        // 判断当扩散圆扩散到指定宽度时添加新扩散圆
        if (mWidths.get(mWidths.size() - 1) >= mMinRadiusWidth + mDiffuseWidth) {
            mWidths.add(mMinRadiusWidth);
            mAlphas.add(255);
        }

        //扩散至只指定宽度或圆圈数过多，则删除第一个
        if (mWidths.get(0) >= mMaxRadiusWidth - 2 || mWidths.size() > 10) {
            mWidths.remove(0);
            mAlphas.remove(0);
        }
        Log.d(TAG, "max Width" + mMaxRadiusWidth);

        if (mIsDiffuse) {
            postInvalidateDelayed(30);
        }
    }

    /**
     * 开始扩散
     */
    public void start() {
        mIsDiffuse = true;
        invalidate();
    }

    /**
     * 停止扩散
     */
    public void stop() {
        mIsDiffuse = false;
    }
}

