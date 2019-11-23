package com.rg.rgview.roundRectCornerView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.rg.rgview.R

/**
 * Create by roger
 * on 2019/11/17
 */
class RoundRectCornerView : FrameLayout {
    constructor(ctx: Context) : this(ctx, null) {

    }

    constructor(ctx: Context, attr: AttributeSet?) : this(ctx, attr, 0) {

    }

    constructor(ctx: Context, attr: AttributeSet?, defStyleAttr: Int) : super(ctx, attr, defStyleAttr) {
        val a: TypedArray = context.obtainStyledAttributes(attr, R.styleable.RoundRectCornerView)
        leftTop = a.getDimension(R.styleable.RoundRectCornerView_leftTopCornerRadius, 0f)
        rightTop = a.getDimension(R.styleable.RoundRectCornerView_rightTopCornerRadius, 0f)
        leftBottom = a.getDimension(R.styleable.RoundRectCornerView_leftBottomCornerRadius, 0f)
        rightBottom = a.getDimension(R.styleable.RoundRectCornerView_rightBottomCornerRadius, 0f)
        a.recycle()
        init()
    }

    private fun init() {
        path = Path()
        rect = RectF()
        setWillNotDraw(false)
    }

    private var leftTop: Float = 0f
    private var rightTop: Float = 0f
    private var leftBottom: Float = 0f
    private var rightBottom: Float = 0f
    private lateinit var path: Path
    private lateinit var rect: RectF

    private fun dp2px(value: Int): Int {
        val v = context.resources.displayMetrics.density
        return (v * value + 0.5f).toInt()
    }

    override fun draw(canvas: Canvas?) {
//        path.moveTo(0f, leftTop)
//        rect.set(0f, 0f, 2 * leftTop, 2 * leftTop)
//        path.arcTo(rect, 180f, 90f)
//        path.lineTo(width - rightTop, 0f)
//        rect.set(width - 2 * rightTop, 0f, width.toFloat(), 2 * rightTop)
//        path.arcTo(rect, 270f, 90f)
//        path.lineTo(width.toFloat(), height - rightBottom)
//        rect.set(width - 2 * rightBottom, height - 2 * rightBottom, width.toFloat(), height.toFloat())
//        path.arcTo(rect, 0f, 90f)
//        path.lineTo(leftBottom, height.toFloat())
//        rect.set(0f, height - 2 * leftBottom, 2 * leftBottom, height.toFloat())
//        path.arcTo(rect, 90f, 90f)
//        path.close()
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        val arr = arrayOf(leftTop, leftTop, rightTop, rightTop, leftBottom, leftBottom, rightBottom, rightBottom)
        path.addRoundRect(rect, arr.toFloatArray(), Path.Direction.CW)
        canvas?.let {
            it.save()
            it.clipPath(path)
            super.draw(canvas)
            canvas.restore()
        }

    }
}