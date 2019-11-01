package com.rg.rgview.transitionManagerAnimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.rg.rgview.R
import kotlinx.android.synthetic.main.activity_scene_transition.*

class SceneTransitionActivity : AppCompatActivity(), View.OnClickListener{


    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var root: ViewGroup




    private var isScene2 = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_transition)
        root = findViewById(R.id.fl_scene_root)
        scene1 = Scene.getSceneForLayout(root, R.layout.activity_scene_transition_scene1, this)
        scene2 = Scene.getSceneForLayout(root, R.layout.activity_scene_transition_scene2, this)
    }

    override fun onClick(v: View?) {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.scene_transition)
        if (isScene2) {
            TransitionManager.go(scene1, transition)
        } else {
            TransitionManager.go(scene2, transition)
        }
        isScene2 = !isScene2;
    }



}
