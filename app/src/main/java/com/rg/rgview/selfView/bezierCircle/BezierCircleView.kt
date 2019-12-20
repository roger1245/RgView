package com.rg.rgview.selfView.bezierCircle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.pow
import kotlin.math.sin

/**
 * Created by roger on 2019/12/19
 */
class BezierCircleView : View {

    constructor(ctx: Context) : this(ctx, null) {
    }

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0) {
    }

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        init()
    }

    private lateinit var paint: Paint
    private lateinit var path: Path
    private lateinit var pl: VPoints
    private lateinit var pr: VPoints
    private lateinit var pt: HPoints
    private lateinit var pb: HPoints

    private val radius: Float = dp2px(25) //圆的半径
    private val expandRadius: Float = dp2px(36) //扩展的椭圆的半径
    var cohesionFactor: Float = 0.1f //由阶段1到阶段2的临界的progress值
    var factor: Float = 0.4f //弹簧的factor
    var progress: Float = 0f //完成度
        set(value) {
            field = value
            invalidate()
        }

    private var startX: Float? = null
    private var startY: Float? = null
    private var endX: Float? = null
    private var endY: Float? = null

    private fun init() {
        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#FF7F7E")

        path = Path()
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        startX = radius * 1.5f
        endX = width - radius * 1.5f
        startY = height / 2f
        endY = height / 2f

        val xStart = startX ?: return
        val xEnd = endX ?: return
        val y = startY ?: return
        pl = VPoints(xStart - radius, y, radius)
        pt = HPoints(xStart, y - radius, radius)
        pr = VPoints(xStart + radius, y, radius)
        pb = HPoints(xStart, y + radius, radius)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.reset()
        val xStart = startX ?: return
        val xEnd = endX ?: return
        if (xStart < xEnd) {
            draggingAlg(pr)
            draggedAlg(pl)
        } else {
            draggingAlg(pl)
            draggedAlg(pr)
        }
        centerAlg(pt, pb)

        path.moveTo(pt.point.x, pt.point.y)
        path.cubicTo(pt.right.x, pt.right.y, pr.top.x, pr.top.y, pr.point.x, pr.point.y)
        path.cubicTo(pr.bottom.x, pr.bottom.y, pb.right.x, pb.right.y, pb.point.x, pb.point.y)
        path.cubicTo(pb.left.x, pb.left.y, pl.bottom.x, pl.bottom.y, pl.point.x, pl.point.y)
        path.cubicTo(pl.top.x, pl.top.y, pt.left.x, pt.left.y, pt.point.x, pt.point.y)
        canvas?.drawPath(path, paint)

    }

    private fun dp2px(value: Int): Float {
        val v = context.resources.displayMetrics.density
        return (v * value + 0.5f)
    }

    private fun draggingAlg(points: VPoints) {
        val xStart = startX ?: return
        val xEnd = endX ?: return
        val rad = if (xStart < xEnd) radius else radius * (-1)
        when {
            progress <= 1 - 2 * cohesionFactor -> {
                points.setX(xStart + (xEnd - xStart) * progress / (1 - 2 * cohesionFactor) + rad)
            }
            else -> {
                points.setX(xEnd + rad)
            }
        }
    }

    private fun centerAlg(top: HPoints, bottom: HPoints) {
        val xStart = startX ?: return
        val xEnd = endX ?: return
        val y = startY ?: return
        when {
            progress <= cohesionFactor -> {
                top.setX(xStart)
                bottom.setX(xStart)
                top.setY(y - radius)
                bottom.setY(y + radius)
            }
            progress <= 1 - cohesionFactor -> {
                val xMove = (xEnd - xStart) * (progress - cohesionFactor) / (1 - 2 * cohesionFactor)
                top.setX(xMove + xStart)
                bottom.setX(xMove + xStart)

            }
            else -> {
                top.setX(xEnd)
                bottom.setX(xEnd)
                top.setY(y - radius)
                bottom.setY(y + radius)
            }
        }
    }

    private fun draggedAlg(points: VPoints) {
        val xStart = startX ?: return
        val xEnd = endX ?: return
        val rad = if (xStart > xEnd) radius else radius * (-1)
        when {
            progress <= 2 * cohesionFactor -> {
                points.setX(xStart + rad)
            }
            progress <= 1 - cohesionFactor -> {
                points.setX(xStart + (xEnd - xStart) * (progress - 2 * cohesionFactor) / (1 - 2 * cohesionFactor) + rad)
            }
            else -> {
                val input = (progress - (1 - cohesionFactor)) / cohesionFactor / 2.7
                val delta = 2.toDouble().pow(-10 * input.toDouble()) * sin((input - factor / 4) * (2 * Math.PI) / factor) + 1
                val base = xStart + (xEnd - xStart) * (1 - 3 * cohesionFactor) / (1 - 2 * cohesionFactor)

                points.setX(base + (xEnd - base) * delta.toFloat() + rad)
            }
        }
    }

    class VPoints(x: Float, y: Float, length: Float) {
        private val CONSTANT = 0.551915024494f
        var point: PointF = PointF(x, y)
        var top: PointF = PointF(x, y - length * CONSTANT)
        var bottom: PointF = PointF(x, y + length * CONSTANT)
        fun setX(x: Float) {
            point.x = x
            top.x = x
            bottom.x = x
        }
    }

    class HPoints(x: Float, y: Float, length: Float) {
        private var lengthRad: Float = length
        private val CONSTANT = 0.551915024494f
        var point: PointF = PointF(x, y)
        var left: PointF = PointF(x - length * CONSTANT, y)
        var right: PointF = PointF(x + length * CONSTANT, y)
        fun setX(x: Float) {
            point.x = x
            left.x = x - lengthRad * CONSTANT
            right.x = x + lengthRad * CONSTANT
        }

        fun setY(y: Float) {
            point.y = y
            left.y = y
            right.y = y
        }
    }

}