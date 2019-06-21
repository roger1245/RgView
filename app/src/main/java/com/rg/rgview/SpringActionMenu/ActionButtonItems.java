package com.rg.rgview.SpringActionMenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ActionButtonItems extends View {
    private static final String TAG = "ActionButtonItems";

    private boolean isSwitchButton;
    private int tag;
    private boolean isOpen;
    private int circleRadius;
    private int dimens;
    private int expandDirect;

    private boolean isPress;


    private Bitmap mBitmap;
    private int iconId;

    private OnActionItemClickListener itemClickListener;

    private Paint mPaint;
    private Path mPath;

    public ActionButtonItems(Context context, int tag,int circleRadius, int dimens, int expandDirect, int iconId, boolean isSwitchButton) {
        this(context);
        this.tag = tag;
        this.isSwitchButton = isSwitchButton;
        this.circleRadius = circleRadius;
        this.dimens = dimens;
        this.expandDirect = expandDirect;
        this.iconId = iconId;

        setBitmapIcon(iconId);

        if (!isSwitchButton) {
            setAlpha(0);
            setVisibility(View.GONE);
        }

//        try {
//            mBitmap = ((BitmapDrawable)getResources().getDrawable(iconId)).getBitmap();
//        } catch (Exception e) {
//        }

    }


    public ActionButtonItems(Context context) {
        this(context, null);
    }

    public ActionButtonItems(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionButtonItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            Rect mDestRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            canvas.drawBitmap(mBitmap, null, mDestRect, mPaint);
        }
    }

    public void setBitmapIcon(int iconId) {

        try {
            mBitmap = ((BitmapDrawable)getResources().getDrawable(iconId)).getBitmap();
        } catch (Exception e) {
        }

        if (circleRadius != 0) {
            mBitmap = getSpecifiedDimen(mBitmap, circleRadius * 2, circleRadius * 2);
        }

        invalidate();
    }

    //根据需要的宽高缩放图片
    public Bitmap getSpecifiedDimen(Bitmap bitmap, int newWidth, int newHeight) {

// 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
        return newbm;

    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setItemClickListener(OnActionItemClickListener itemClickListener) {
        if (itemClickListener == null) {
            return;
        }
        this.itemClickListener = itemClickListener;
    }
    // click listener

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPress = true;
                invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if (!isPress) {
                    return true;
                }
                invalidate();
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(tag);
                }

                if (isSwitchButton && !isOpen) {
                    ((ActionMenu)getParent()).openMenu();
                } else if ((isSwitchButton && isOpen) || !isSwitchButton){
                    ((ActionMenu)getParent()).closeMenu();
                }
                isPress = false;
                return true;
        }


        return super.onTouchEvent(event);
    }
}
