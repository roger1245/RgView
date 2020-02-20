package com.rg.rgview.selfView.gpa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rg.rgview.R
import kotlinx.android.synthetic.main.activity_gpagraph.*
import kotlinx.android.synthetic.main.layout_gpa_bottom.*

class GPAGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpagraph)
        gpa_graph.array = arrayListOf(4.0F, 3.5F, 3F, 2.5F, 2F, 1.8F, 1.5F, 1.2F)
        gpa_graph.bindCoordinator(coordinator)

    }
}
