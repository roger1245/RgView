package com.rg.rgview.edgeEffect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rg.rgview.R
import kotlinx.android.synthetic.main.activity_edge_effect_factory.*

class EdgeEffectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edge_effect_factory)
        val list = mutableListOf<String>()
        for (i in 1..20) {
            list.add("ff")
        }
        val adapter = CheeseAdapter(list)
        rl_edge_effect.adapter = adapter
        rl_edge_effect.edgeEffectFactory = adapter.edgeEffectFactory
    }
}
