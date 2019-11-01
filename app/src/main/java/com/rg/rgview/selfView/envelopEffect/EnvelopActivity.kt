package com.rg.rgview.selfView.envelopEffect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.rg.rgview.R

class EnvelopActivity : AppCompatActivity() {
    private lateinit var effect: EnvelopViewGroup
//    private lateinit var iv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_envelop)
        effect = findViewById(R.id.envelop_effect2)
//        iv = findViewById(R.id.envelop_iv)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        effect.openAnimation()

    }
}
