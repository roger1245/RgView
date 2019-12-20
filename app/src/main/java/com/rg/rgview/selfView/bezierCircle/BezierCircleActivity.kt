package com.rg.rgview.selfView.bezierCircle

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rg.rgview.R
import kotlinx.android.synthetic.main.activity_bezier_circle.*

class BezierCircleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bezier_circle)
        btn_bezier_circle.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(bezier_circle, "progress", 0f, 1f)
            animator.duration = 2000
            animator.start()
        }
    }

}
