package com.rg.rgview.transitionManagerAnimation

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import android.transition.Explode
import android.transition.TransitionInflater
import android.view.View
import android.view.Window
import com.rg.rgview.R
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.scene -> {
                val intent = Intent(this@AnimationActivity, SceneTransitionActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.delayed_transition -> {
                val intent = Intent(this@AnimationActivity, DelayedTransitionActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

            }
            R.id.shared_element_transition -> {
                val intent = Intent(this@AnimationActivity, SharedElementActivity::class.java)

                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, shared_element_transition, "btn_shared").toBundle())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            exitTransition = TransitionInflater.from(this@AnimationActivity).inflateTransition(R.transition.scene_transition)
        }
        setContentView(R.layout.activity_animation)
    }
}
