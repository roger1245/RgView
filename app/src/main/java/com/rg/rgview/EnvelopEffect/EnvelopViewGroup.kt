package com.rg.rgview.EnvelopEffect

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup


/**
 * Create by roger
 * on 2019/8/9
 */
class EnvelopViewGroup : ViewGroup {

    private val radiusWidth = dp2px(70)
    private val radiusHeight = dp2px(25)

    private val duration: Long = 2500

    constructor(ctx: Context): this(ctx, null) {

    }
    constructor(ctx: Context, attrs: AttributeSet?): this(ctx, attrs, 0) {
    }

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {

    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val cCount = childCount
        var cWidth = 0
        var cHeight = 0
        var cParams: MarginLayoutParams? = null

        for (i  in 0 until cCount) {
            val childView = getChildAt(i)
            cWidth = childView.measuredWidth
            cHeight = childView.measuredHeight
            val centerX = width / 2
            val centerY = height / 2
            var cl = 0
            var ct = 0
            var cr = 0
            var cb = 0
            when (i) {
                0 -> {
                    cl = 0
                    ct = 0
                    cr = width
                    cb = height
                }
                1, 2 -> {
                    cl = centerX - radiusWidth
                    ct = centerY - radiusHeight
                    cr = centerX + radiusWidth
                    cb = centerY + radiusHeight
                }
                3 -> {
                    cl = centerX - radiusWidth
                    ct = centerY - radiusHeight
                    cr = cl + cWidth
                    cb = ct + cHeight
                }
                4 -> {
                    cr = centerX + radiusWidth
                    ct = centerY - radiusHeight
                    cl = cr - cWidth
                    cb = ct + cHeight
                }
                5 -> {
                    cl = centerX - radiusWidth
                    cb = centerY + radiusHeight
                    cr = cl + cWidth
                    ct = cb - cHeight
                }
                6 -> {
                    cr = centerX + radiusWidth
                    cb = centerY + radiusHeight
                    cl = cr - cWidth
                    ct = cb - cHeight
                }


            }
            childView.layout(cl, ct, cr, cb)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)


        setMeasuredDimension(sizeWidth, sizeHeight)

    }

    fun openAnimation() {
        val cCount = childCount
        var cWidth = 0
        var cHeight = 0
        for (i in 0 until cCount) {
            val childView = getChildAt(i)
            cWidth = childView.measuredWidth
            cHeight = childView.measuredHeight
            val centerX = width / 2
            val centerY = height / 2
            var cl = 0
            var ct = 0
            var cr = 0
            var cb = 0
            when (i) {
                0 -> {
                    childView.animate().alpha(1F).setDuration(duration).setStartDelay(3000).start()
                }
                1 -> {
                    val ratioX = width / cWidth.toDouble()
                    val ratioY = height / cHeight.toDouble()
                    childView.animate().scaleX(ratioX.toFloat()).scaleY(ratioY.toFloat()).setDuration(duration).start()
                    childView.animate().alpha(0F).setDuration(500).start()


                }

                2 -> {
                    val ratioX = width / cWidth.toDouble()
                    val ratioY = height / cHeight.toDouble()
                    childView.animate().scaleX(ratioX.toFloat()).scaleY(ratioY.toFloat()).setDuration(duration).start()
                }


                3 -> {
                    cl = centerX - radiusWidth
                    ct = centerY - radiusHeight
                    cr = cl + cWidth
                    cb = ct + cHeight
                    childView.animate().translationX(-(cl).toFloat()).translationY(-(ct).toFloat()).setDuration(duration).start()
                }
                4 -> {
                    cr = centerX + radiusWidth
                    ct = centerY - radiusHeight
                    cl = cr - cWidth
                    cb = ct + cHeight
                    childView.animate().translationX((width - cr).toFloat()).translationY(-(ct).toFloat()).setDuration(duration).start()

                }
                5 -> {
                    cl = centerX - radiusWidth
                    cb = centerY + radiusHeight
                    cr = cl + cWidth
                    ct = cb - cHeight
                    childView.animate().translationX(-(cl).toFloat()).translationY((height - cb).toFloat()).setDuration(duration).start()


                }
                6 -> {
                    cr = centerX + radiusWidth
                    cb = centerY + radiusHeight
                    cl = cr - cWidth
                    ct = cb - cHeight
                    childView.animate().translationX((width - cr).toFloat()).translationY((height - cb).toFloat()).setDuration(duration).start()
                }


            }
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    fun dp2px(value: Int): Int {
        val v = context.resources.displayMetrics.density
        return (v * value + 0.5f).toInt()
    }
}