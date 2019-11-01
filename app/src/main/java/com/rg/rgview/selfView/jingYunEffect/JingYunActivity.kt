package com.rg.rgview.selfView.jingYunEffect

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Outline
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.rg.rgview.R

class JingYunActivity : AppCompatActivity() {

    private var diffuseView: DiffuseView? = null
    private var roundImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jing_yun)

        init()
    }

    private fun init() {
        roundImage = findViewById(R.id.front_image_view)
        Glide.with(this).load("https://raw.githubusercontent.com/roger1245/ImgBed/master/img/rgview_2.jpg").into(roundImage!!)

        //图片裁剪和旋转
        if (Build.VERSION.SDK_INT >= 21) {
            //裁剪
            roundImage!!.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val width = roundImage!!.width
                    val height = roundImage!!.height
                    outline.setOval(0, 0, width, height)
                }
            }
            roundImage!!.clipToOutline = true

            //属性动画让roundImage旋转起来
            val objectAnimator = ObjectAnimator.ofFloat(roundImage!!, "rotation", 0.0f, 360.0f)
            objectAnimator.setDuration(15000)
            objectAnimator.setRepeatMode(ValueAnimator.RESTART)
            objectAnimator.setInterpolator(LinearInterpolator())
            objectAnimator.setRepeatCount(-1)
            objectAnimator.start()
        }

        diffuseView = findViewById<DiffuseView>(R.id.front_diffuseview)
        diffuseView!!.start()
    }
}
