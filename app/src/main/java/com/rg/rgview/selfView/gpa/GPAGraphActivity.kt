package com.rg.rgview.selfView.gpa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rg.rgview.R
import kotlinx.android.synthetic.main.layout_gpa_bottom.*

class GPAGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpagraph)
//        gpa_graph.setData(arrayListOf(4.0F, 3.5F, 3F, 2.5F, 2F, 1.8F, 1.5F, 1.2F))
        gpa_graph.setData(arrayListOf(88F, 99F, 77F, 44F, 100F, 98F, 99F, 89F))
        gpa_graph.setRule(object : GraphRule() {
            override fun mappingRule(old: Float): Float {
                return old / 25
            }
        })
        gpa_graph.invalidate()
    }
}
