package com.rg.rgview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fab_transformation.*

class FabTransformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fab_transformation)


        fab.setOnClickListener {
            fab.isExpanded = true
        }
        scrim.setOnClickListener {
            fab.isExpanded = false
        }
    }

    override fun onBackPressed() {
        if (fab.isExpanded) {
            fab.isExpanded = false
        } else {
            super.onBackPressed()
        }
    }

}
