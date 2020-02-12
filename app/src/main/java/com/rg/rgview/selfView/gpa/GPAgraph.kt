package com.rg.rgview.selfView.gpa

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.rg.rgview.widget.dp2px
import org.jetbrains.anko.backgroundColor

/**
 * Created by roger on 2020/2/11
 */
class GPAgraph : View {

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        init()
    }


    private lateinit var gpaPaint: Paint
    private val mWidth: Int = dp2px(100)
    private val mHeight: Int = dp2px(100)
    private val array = arrayListOf(3.5F, 2.4F, 1.5F, 2.6F, 3.3F, 3.2F, 1.1F, 0.9F)
    private lateinit var path: Path
    private lateinit var pathBlue: Path
    private val bottomWidth = dp2px(50)
    private var segHeight: Int = 0
    private var segWidth: Int = 0
    private lateinit var dashPaint: Paint
    private val textArray = arrayListOf("大一上", "大一下", "大二上", "大二下", "大三上", "大三下", "大四上", "大四下")
    private lateinit var textPaint: Paint
    private lateinit var gradientPaint: Paint
    private lateinit var linearGradient: LinearGradient

    private fun init() {
        backgroundColor = Color.WHITE

        gpaPaint = Paint()
        gpaPaint.color = Color.parseColor("#2921D1")
        gpaPaint.style = Paint.Style.STROKE
        gpaPaint.strokeWidth = dp2px(3).toFloat()

        path = Path()
        pathBlue = Path()

        dashPaint = Paint()
        dashPaint.color = Color.parseColor("#2915315B")
        dashPaint.style = Paint.Style.STROKE
        dashPaint.strokeWidth = dp2px(1).toFloat()
        dashPaint.pathEffect = DashPathEffect(floatArrayOf(dp2px(3).toFloat(), dp2px(2).toFloat()), 0F)

        textPaint = Paint()
        textPaint.color = Color.parseColor("#A415315B")
        textPaint.textSize = dp2px(10).toFloat()
        textPaint.textAlign = Paint.Align.CENTER

        gradientPaint = Paint()
        gradientPaint.style = Paint.Style.FILL

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec), resolveSize(mHeight, heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val canvas = canvas ?: return
        canvas.save()
        path.reset()
        pathBlue.reset()
        canvas.translate(0F, (height - bottomWidth).toFloat())

        drawDashLine(canvas)
        drawText(canvas)
        drawGPAPath(canvas)

        canvas.restore()
    }

    private fun drawGPAPath(canvas: Canvas) {
        path.moveTo(0F, -3F * segHeight)
        for (item in array.withIndex()) {
            path.lineTo(segWidth * (item.index + 1F), -1 * segHeight * item.value)
        }
        path.lineTo(width.toFloat(), -1 * array.last() * segHeight)
        pathBlue.addPath(path)

        path.lineTo(segWidth * 9F, 0F)
        path.lineTo(0F, 0F)
        path.close()

        gradientPaint.shader = linearGradient
        canvas.drawPath(path, gradientPaint)

        //画点
        gpaPaint.style = Paint.Style.FILL
        for (item in array.withIndex()) {
            canvas.drawCircle((item.index + 1) * segWidth.toFloat(), -1 * item.value * segHeight.toFloat(), dp2px(4).toFloat(), gpaPaint)
        }
        gpaPaint.style = Paint.Style.STROKE
        canvas.drawPath(pathBlue, gpaPaint)

    }

    private fun drawDashLine(canvas: Canvas) {
        for (i in 0..3) {
            canvas.drawLine(0F, -1 * i * segHeight.toFloat(), width.toFloat(), -1 * i * segHeight.toFloat(), dashPaint)
        }

    }

    private fun drawText(canvas: Canvas) {

        for (item in textArray.withIndex()) {
            canvas.drawText(textArray[item.index], (item.index + 1) * segWidth.toFloat(), bottomWidth / 2F, textPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        segHeight = (h - bottomWidth) / 4
        segWidth = w / 9

        linearGradient = LinearGradient(0F, 0F, 0F, -1F * h, Color.parseColor("#FFFFFFFF"), Color.parseColor("#33A19EFF"), Shader.TileMode.REPEAT)
    }
}