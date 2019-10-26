package com.rg.rgview.selfView.springActionMenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.rg.rgview.R

class SpringActivity : AppCompatActivity() {
    private var actionMenu: ActionMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring)

        actionMenu = findViewById(R.id.action_menu)
        actionMenu!!.addView(R.drawable.ic_mood_clam)
        actionMenu!!.addView(R.drawable.ic_mood_exciting)
        actionMenu!!.addView(R.drawable.ic_mood_happy)
        actionMenu!!.addView(R.drawable.ic_mood_unhappy)
        actionMenu!!.setItemClickListener(object : OnActionItemClickListener {
            override fun onItemClick(index: Int) {
                val actionButtonItems = actionMenu!!.getChildAt(0) as ActionButtonItems
                when (index) {
                    1 -> actionButtonItems.setBitmapIcon(R.drawable.ic_mood_clam)
                    2 -> actionButtonItems.setBitmapIcon(R.drawable.ic_mood_exciting)
                    3 -> actionButtonItems.setBitmapIcon(R.drawable.ic_mood_happy)
                    4 -> actionButtonItems.setBitmapIcon(R.drawable.ic_mood_unhappy)
                    else -> {
                    }
                }
            }

            override fun onAnimationEnd(isOpen: Boolean) {

            }
        })

    }

    private fun showMessage(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }
}
