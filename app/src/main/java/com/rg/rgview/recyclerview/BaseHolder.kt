package com.rg.rgview.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @CreateBy: FxyMine4ever
 *
 * @CreateAt:2018/9/16
 */
class BaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun getHolder(context: Context,
                      parent: ViewGroup,
                      layoutIds: Int): BaseHolder {
            return BaseHolder(LayoutInflater.from(context).inflate(layoutIds, parent, false))
        }
    }
}