package com.rg.rgview.selfView.springActionMenu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ActionButtonItems @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var isSwitchButton: Boolean? = null
    private var tag: Int? = null
    private var isOpen: Boolean = false
    private var circleRadius: Int? = null
    private var dimens: Int? = null
    private var expandDirect: Int? = null

    private var isPress: Boolean = false


    private var mBitmap: Bitmap? = null
    private var iconId: Int? = null

    private var itemClickListener: OnActionItemClickListener? = null

    private var mPaint: Paint? = null
    private val mPath: Path? = null

    constructor(context: Context, tag: Int, circleRadius: Int, dimens: Int, expandDirect: Int, iconId: Int, isSwitchButton: Boolean) : this(context) {
        this.tag = tag
        this.isSwitchButton = isSwitchButton
        this.circleRadius = circleRadius
        this.dimens = dimens
        this.expandDirect = expandDirect
        this.iconId = iconId

        setBitmapIcon(iconId)

        if (!isSwitchButton) {
            alpha = 0f
            visibility = View.GONE
        }

        //        try {
        //            mBitmap = ((BitmapDrawable)getResources().getDrawable(iconId)).getBitmap();
        //        } catch (Exception e) {
        //        }

    }

    init {
        init()
    }

    private fun init() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isFilterBitmap = true
        mPaint!!.isDither = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBitmap != null) {
            val mDestRect = Rect(0, 0, mBitmap!!.width, mBitmap!!.height)
            canvas.drawBitmap(mBitmap!!, null, mDestRect, mPaint)
        }
    }

    fun setBitmapIcon(iconId: Int) {

        try {
            mBitmap = (resources.getDrawable(iconId) as BitmapDrawable).bitmap
        } catch (e: Exception) {
        }

        if (circleRadius != 0) {
            mBitmap = getSpecifiedDimen(mBitmap!!, 2 * circleRadius!!, circleRadius!! * 2)
        }

        invalidate()
    }

    //根据需要的宽高缩放图片
    fun getSpecifiedDimen(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {

        // 获得图片的宽高
        val width = bitmap.width
        val height = bitmap.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)

    }

    fun setOpen(isOpen: Boolean) {
        this.isOpen = isOpen
    }

    fun setItemClickListener(itemClickListener: OnActionItemClickListener?) {
        if (itemClickListener == null) {
            return
        }
        this.itemClickListener = itemClickListener
    }
    // click listener

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isPress = true
                invalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
            }

            MotionEvent.ACTION_UP -> {
                if (!isPress) {
                    return true
                }
                invalidate()
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(tag!!)
                }

                if (isSwitchButton!! && !isOpen) {
                    (parent as ActionMenu).openMenu()
                } else if (isSwitchButton!! && isOpen || !isSwitchButton!!) {
                    (parent as ActionMenu).closeMenu()
                }
                isPress = false
                return true
            }
        }


        return super.onTouchEvent(event)
    }

    companion object {
        private val TAG = "ActionButtonItems"
    }
}
