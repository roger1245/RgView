package com.rg.rgview.selfView.gpa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rg.rgview.R
import kotlinx.android.synthetic.main.layout_gpa_bottom.*

class GPAGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpagraph)
        val list = arrayListOf("68", "88", "48", "98", "78", "88", "38", "68")
        gpa_graph.setData(list)
        gpa_graph.setRule(AverageRule(list))
        gpa_graph.invalidate()
    }
}
