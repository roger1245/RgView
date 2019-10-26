package com.rg.rgview.selfView.springActionMenu

interface OnActionItemClickListener {

    fun onItemClick(index: Int)

    fun onAnimationEnd(isOpen: Boolean)
}