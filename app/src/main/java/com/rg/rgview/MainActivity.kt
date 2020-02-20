package com.rg.rgview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rg.rgview.fab.FabTransformationActivity
import com.rg.rgview.reoder.ReorderActivity
import com.rg.rgview.selfView.SelfViewActivity
import com.rg.rgview.transitionManagerAnimation.AnimationActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.self_view -> {
                val intent = Intent(this@MainActivity, SelfViewActivity::class.java)
                startActivity(intent)
            }
            R.id.transition_animation -> {
                val intent = Intent(this@MainActivity, AnimationActivity::class.java)
                startActivity(intent)
            }
            R.id.fab_transformation -> {
                val intent = Intent(this@MainActivity, FabTransformationActivity::class.java)
                startActivity(intent)
            }
            R.id.reorder -> {
                val intent = Intent(this@MainActivity, ReorderActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
