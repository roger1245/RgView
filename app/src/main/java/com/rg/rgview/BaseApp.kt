package com.rg.rgview

import android.app.Application
import android.content.Context

/**
 * Created by roger on 2020/2/11
 */
class BaseApp : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?.let { context = it }
    }
}