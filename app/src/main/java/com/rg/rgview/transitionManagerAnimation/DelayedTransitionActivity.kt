package com.rg.rgview.transitionManagerAnimation

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.rg.rgview.R
import kotlinx.android.synthetic.main.activity_scene_transition_scene1.*

class DelayedTransitionActivity : AppCompatActivity(), View.OnClickListener {


    private var isState2 = false

    private lateinit var root: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delayed_transition)
        root = findViewById(R.id.fl_scene_root)
    }

    override fun onClick(v: View?) {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.scene_transition)
        TransitionManager.beginDelayedTransition(root, transition)
        changeScene()
    }

    private fun changeScene() {
        if (isState2) {
            iv_1.scaleX = 3f
            iv_1.rotation = 1f
            iv_cheese.clipBounds = Rect(0, 0, 300, 300)
            iv_cheese_2.scaleType = ImageView.ScaleType.FIT_XY
        } else {
            iv_1.scaleX = 1f
            iv_1.rotation = 179f
            iv_cheese.clipBounds = Rect(240, 240, 540, 540)
            iv_cheese_2.scaleType = ImageView.ScaleType.CENTER_INSIDE


        }
        isState2 = !isState2
    }
}
