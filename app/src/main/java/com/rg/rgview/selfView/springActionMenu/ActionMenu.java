package com.rg.rgview.selfView.springActionMenu;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

import com.rg.rgview.R;

public class ActionMenu extends ViewGroup {
    public static final int expandDirectTop = 0;        // top
    public static final int expandDirectDown = 1;        // down
    public static final int expandDirectLeft = 2;        // left
    public static final int expandDirectRight = 3;        // right

    private int childViewCount;             // button count
    private boolean isOpen = false;
    private int expandDirect;
    private Context context;
    private int circleRadius;
    private int dimens;
    private long duration;


    private OnActionItemClickListener itemClickListener;

    public ActionMenu(Context context) {
        this(context, null);
    }

    public ActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < childViewCount; i++) {
            ActionButtonItems items = (ActionButtonItems)getChildAt(i);

            switch (expandDirect) {

                case expandDirectTop:
                    l = 0;
                    t = 2 * circleRadius * (childViewCount - 1) + dimens * (childViewCount - 1);
                    r = circleRadius * 2;
                    b = t + circleRadius * 2;
                    break;

                case expandDirectDown:
                    l = 0;
                    t = 0;
                    r = circleRadius * 2;
                    b = t + circleRadius * 2;
                    break;

                case expandDirectLeft:
                    l = 2 * circleRadius * (childViewCount - 1) + dimens * (childViewCount - 1);
                    t = 0;
                    r = l + circleRadius * 2;
                    b = circleRadius * 2;
                    break;

                case expandDirectRight:
                    l = 0;
                    t = 0;
                    r = l + circleRadius * 2 ;
                    b = circleRadius * 2;
                    break;
            }
            items.layout(l, t, r, b);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        // set xml property
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionMenu);
        circleRadius = (int) typedArray.getDimension(R.styleable.ActionMenu_circleRadius, 30.f);
        dimens = (int) typedArray.getDimension(R.styleable.ActionMenu_dimens, 30.f);
        duration = typedArray.getInteger(R.styleable.ActionMenu_animationDuration, 500);
        expandDirect = typedArray.getInteger(R.styleable.ActionMenu_expandDirect, 0);
        int menuIcon = typedArray.getResourceId(R.styleable.ActionMenu_actionMenuIcon, -1);

        // add root button
        addView(new ActionButtonItems(context, 0, circleRadius, dimens, expandDirect, menuIcon,true));
        childViewCount = 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width, height;
        if (expandDirect == expandDirectTop || expandDirect ==expandDirectDown) {
            width = circleRadius * 2;
            height = (childViewCount - 1) * (circleRadius * 2 + dimens) + circleRadius * 2;
        } else {
            width = (childViewCount - 1) * (circleRadius * 2 + dimens) + circleRadius * 2;
            height = circleRadius * 2;
        }
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        //    setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0), resolveSizeAndState(height, heightMeasureSpec, 0));
        setMeasuredDimension(width, height);
    }

    /**
     * buttonItem open animation
     */
    private void buttonItemOpenAnimation(int index,ActionButtonItems view) {
        if (index == 0) {
//            view.startFactorAnimation(duration / 6, 0, 1);
        } else {

            ViewPropertyAnimator propertyAnimator = view.animate().alpha(1).
                    setInterpolator(new LinearInterpolator()).setDuration(duration / 5);

            switch (expandDirect) {
                case expandDirectTop:
                    propertyAnimator.y(2 * circleRadius * (childViewCount - index) + dimens * (childViewCount - 1 - index ) - 2 * circleRadius);
                    break;
                case expandDirectDown:
                    propertyAnimator.y(circleRadius * 2 * index + dimens * (index));
                    break;
                case expandDirectLeft:
                    propertyAnimator.x(2 * circleRadius * (childViewCount - index) + dimens * (childViewCount - 1 - index) - 2 * circleRadius);
                    break;
                case expandDirectRight:
                    propertyAnimator.x(circleRadius * 2 * index + dimens * (index));
                    break;
            }
            if (isOpen) {
                view.setVisibility(View.VISIBLE);
            }

            propertyAnimator.start();

        }
        view.setOpen(isOpen);
    }

    /**
     * buttonItem close animation
     */
    private void buttonItemCloseAnimation(int index, final ActionButtonItems view) {
        if (index == 0) {
//            view.startFactorAnimation(duration / 6, 0, -1);
        } else {
            ViewPropertyAnimator propertyAnimator = view.animate().alpha(0).setDuration(duration / 3);

            switch (expandDirect) {
                case expandDirectTop:
                    propertyAnimator.y((circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2);
                    break;
                case expandDirectDown:
                    propertyAnimator.y(dimens * (- 1));
                    break;
                case expandDirectLeft:
                    propertyAnimator.x((circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2);
                    break;
                case expandDirectRight:
                    propertyAnimator.x(dimens * (- 1));
                    break;
            }

            propertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!isOpen) {
                        view.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            propertyAnimator.start();
        }
        view.setOpen(isOpen);
    }

    /**
     * open menu
     */
    public void openMenu() {
        isOpen = true;
        for (int i = 0; i < childViewCount; i++) {
            buttonItemOpenAnimation(i, (ActionButtonItems)getChildAt(i));
        }
    }

    /**
     * close menu
     */
    public void closeMenu() {
        isOpen = false;
        for (int i = 0; i < childViewCount; i++) {
            buttonItemCloseAnimation(i, (ActionButtonItems)getChildAt(i));
        }
    }

    public void addView(int drawableIcon, boolean isSwitchButton) {
        addView(new ActionButtonItems(context, childViewCount, circleRadius, dimens, expandDirect, drawableIcon, isSwitchButton));
        childViewCount ++;
    }

    public void addView(int drawableIcon) {
        addView(drawableIcon, false);
    }

    /**
     * set item click listener
     */
    public void setItemClickListener(OnActionItemClickListener itemClickListener) {
        if (itemClickListener == null) {
            return;
        }
        this.itemClickListener = itemClickListener;
        for (int i = 0; i < childViewCount; i++) {
            ActionButtonItems view = (ActionButtonItems) getChildAt(i);
            view.setItemClickListener(itemClickListener);
        }
    }


}
