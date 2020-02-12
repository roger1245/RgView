package com.rg.rgview.widget

import com.rg.rgview.BaseApp

/**
 * Created by roger on 2020/2/11
 */
fun dp2px(value: Int): Int {
    val v = BaseApp.context.resources.displayMetrics.density
    return (v * value + 0.5f).toInt()
}