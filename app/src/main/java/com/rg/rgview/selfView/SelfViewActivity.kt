package com.rg.rgview.selfView

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rg.rgview.R
import com.rg.rgview.selfView.bubbleEffect.BubbleActivity
import com.rg.rgview.selfView.envelopEffect.EnvelopActivity
import kotlinx.android.synthetic.main.activity_self_view.*

class SelfViewActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.jingyun_effect -> {

            }
            R.id.spring_action_menu -> {
                val intent = Intent(this@SelfViewActivity, Spring)
            }
            R.id.envelop_effect -> {
                val intent = Intent(this@SelfViewActivity, EnvelopActivity::class.java)
                startActivity(intent)
            }
            R.id.bubble_effect -> {
                val intent = Intent(this@SelfViewActivity, BubbleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_view)
    }


}
