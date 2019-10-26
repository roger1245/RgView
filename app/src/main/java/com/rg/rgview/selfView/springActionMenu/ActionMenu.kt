package com.rg.rgview.selfView.springActionMenu

import android.animation.Animator
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.LinearInterpolator

import com.rg.rgview.R

class ActionMenu constructor(private var context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private var childViewCount: Int = 0             // button count
    private var isOpen = false
    private var expandDirect: Int = 0
    private var circleRadius: Int = 0
    private var dimens: Int = 0
    private var duration: Long = 0


    private var itemClickListener: OnActionItemClickListener? = null

    init {
        init(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var l = l
        var t = t
        var r = r
        var b = b
        for (i in 0 until childViewCount) {
            val items = getChildAt(i) as ActionButtonItems

            when (expandDirect) {

                expandDirectTop -> {
                    l = 0
                    t = 2 * circleRadius * (childViewCount - 1) + dimens * (childViewCount - 1)
                    r = circleRadius * 2
                    b = t + circleRadius * 2
                }

                expandDirectDown -> {
                    l = 0
                    t = 0
                    r = circleRadius * 2
                    b = t + circleRadius * 2
                }

                expandDirectLeft -> {
                    l = 2 * circleRadius * (childViewCount - 1) + dimens * (childViewCount - 1)
                    t = 0
                    r = l + circleRadius * 2
                    b = circleRadius * 2
                }

                expandDirectRight -> {
                    l = 0
                    t = 0
                    r = l + circleRadius * 2
                    b = circleRadius * 2
                }
            }
            items.layout(l, t, r, b)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        // set xml property
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionMenu)
        circleRadius = typedArray.getDimension(R.styleable.ActionMenu_circleRadius, 30f).toInt()
        dimens = typedArray.getDimension(R.styleable.ActionMenu_dimens, 30f).toInt()
        duration = typedArray.getInteger(R.styleable.ActionMenu_animationDuration, 500).toLong()
        expandDirect = typedArray.getInteger(R.styleable.ActionMenu_expandDirect, 0)
        val menuIcon = typedArray.getResourceId(R.styleable.ActionMenu_actionMenuIcon, -1)

        // add root button
        addView(ActionButtonItems(context, 0, circleRadius, dimens, expandDirect, menuIcon, true))
        childViewCount = 1
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width: Int
        var height: Int
        if (expandDirect == expandDirectTop || expandDirect == expandDirectDown) {
            width = circleRadius * 2
            height = (childViewCount - 1) * (circleRadius * 2 + dimens) + circleRadius * 2
        } else {
            width = (childViewCount - 1) * (circleRadius * 2 + dimens) + circleRadius * 2
            height = circleRadius * 2
        }
        width += paddingLeft + paddingRight
        height += paddingTop + paddingBottom
        //    setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0), resolveSizeAndState(height, heightMeasureSpec, 0));
        setMeasuredDimension(width, height)
    }

    /**
     * buttonItem open animation
     */
    private fun buttonItemOpenAnimation(index: Int, view: ActionButtonItems) {
        if (index == 0) {
            //            view.startFactorAnimation(duration / 6, 0, 1);
        } else {

            val propertyAnimator = view.animate().alpha(1f).setInterpolator(LinearInterpolator()).setDuration(duration / 5)

            when (expandDirect) {
                expandDirectTop -> propertyAnimator.y((2 * circleRadius * (childViewCount - index) + dimens * (childViewCount - 1 - index) - 2 * circleRadius).toFloat())
                expandDirectDown -> propertyAnimator.y((circleRadius * 2 * index + dimens * index).toFloat())
                expandDirectLeft -> propertyAnimator.x((2 * circleRadius * (childViewCount - index) + dimens * (childViewCount - 1 - index) - 2 * circleRadius).toFloat())
                expandDirectRight -> propertyAnimator.x((circleRadius * 2 * index + dimens * index).toFloat())
            }
            if (isOpen) {
                view.setVisibility(View.VISIBLE)
            }

            propertyAnimator.start()

        }
        view.setOpen(isOpen)
    }

    /**
     * buttonItem close animation
     */
    private fun buttonItemCloseAnimation(index: Int, view: ActionButtonItems) {
        if (index == 0) {
            //            view.startFactorAnimation(duration / 6, 0, -1);
        } else {
            val propertyAnimator = view.animate().alpha(0f).setDuration(duration / 3)

            when (expandDirect) {
                expandDirectTop -> propertyAnimator.y(((circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2).toFloat())
                expandDirectDown -> propertyAnimator.y((dimens * -1).toFloat())
                expandDirectLeft -> propertyAnimator.x(((circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2).toFloat())
                expandDirectRight -> propertyAnimator.x((dimens * -1).toFloat())
            }

            propertyAnimator.setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    if (!isOpen) {
                        view.setVisibility(View.GONE)
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })

            propertyAnimator.start()
        }
        view.setOpen(isOpen)
    }

    /**
     * open menu
     */
    fun openMenu() {
        isOpen = true
        for (i in 0 until childViewCount) {
            buttonItemOpenAnimation(i, getChildAt(i) as ActionButtonItems)
        }
    }

    /**
     * close menu
     */
    fun closeMenu() {
        isOpen = false
        for (i in 0 until childViewCount) {
            buttonItemCloseAnimation(i, getChildAt(i) as ActionButtonItems)
        }
    }

    @JvmOverloads
    fun addView(drawableIcon: Int, isSwitchButton: Boolean = false) {
        addView(ActionButtonItems(context, childViewCount, circleRadius, dimens, expandDirect, drawableIcon, isSwitchButton))
        childViewCount++
    }

    /**
     * set item click listener
     */
    fun setItemClickListener(itemClickListener: OnActionItemClickListener?) {
        if (itemClickListener == null) {
            return
        }
        this.itemClickListener = itemClickListener
        for (i in 0 until childViewCount) {
            val view = getChildAt(i) as ActionButtonItems
            view.setItemClickListener(itemClickListener)
        }
    }

    companion object {
        val expandDirectTop = 0        // top
        val expandDirectDown = 1        // down
        val expandDirectLeft = 2        // left
        val expandDirectRight = 3        // right
    }


}
