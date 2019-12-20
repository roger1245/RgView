package com.rg.rgview.selfView

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rg.rgview.R
import com.rg.rgview.selfView.bezierCircle.BezierCircleActivity
import com.rg.rgview.selfView.bubbleEffect.BubbleActivity
import com.rg.rgview.selfView.envelopEffect.EnvelopActivity
import com.rg.rgview.selfView.jingYunEffect.JingYunActivity
import com.rg.rgview.selfView.springActionMenu.SpringActivity

class SelfViewActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.jingyun_effect -> {
                val intent = Intent(this@SelfViewActivity, JingYunActivity::class.java)
                startActivity(intent)
            }
            R.id.spring_action_menu -> {
                val intent = Intent(this@SelfViewActivity, SpringActivity::class.java)
                startActivity(intent)
            }
            R.id.envelop_effect -> {
                val intent = Intent(this@SelfViewActivity, EnvelopActivity::class.java)
                startActivity(intent)
            }
            R.id.bubble_effect -> {
                val intent = Intent(this@SelfViewActivity, BubbleActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_bezier_circle -> {
                val intent = Intent(this@SelfViewActivity, BezierCircleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_view)
    }


}
