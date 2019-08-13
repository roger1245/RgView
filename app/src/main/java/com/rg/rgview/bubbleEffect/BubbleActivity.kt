package com.rg.rgview.bubbleEffect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rg.rgview.R
import org.jetbrains.anko.find

class BubbleActivity : AppCompatActivity() {
    private lateinit var bubbles: BubbleView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)
        bubbles = find(R.id.bubble_view)

    }

    override fun onPostResume() {
        super.onPostResume()
        bubbles.start()
    }

    override fun onStop() {
        bubbles.stop()
        super.onStop()
    }
}
