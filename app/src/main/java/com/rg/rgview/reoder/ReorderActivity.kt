package com.rg.rgview.reoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rg.rgview.R
import com.rg.rgview.widget.SpaceDecoration

class ReorderActivity : AppCompatActivity() {

    private val viewModel: ReorderViewModel by viewModels()

    private var pickUpElevation: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reorder)

        val list: RecyclerView = findViewById(R.id.rv_reorder)
        pickUpElevation = resources.getDimensionPixelSize(R.dimen.pick_up_elevation).toFloat()
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(list)

        list.addItemDecoration(
            SpaceDecoration(resources.getDimensionPixelSize(R.dimen.spacing_small))
        )
        val adapter = CheeseGridAdapter(onItemLongClick = { holder ->
            itemTouchHelper.startDrag(holder)
        })
        list.adapter = adapter

        viewModel.cheeses.observe(this) { cheeses  ->

            adapter.submitList(cheeses)
        }


    }

    private val touchHelperCallback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(ItemTouchHelper.UP
                                    or ItemTouchHelper.DOWN
                                    or ItemTouchHelper.LEFT
                                    or ItemTouchHelper.RIGHT,
            0
            )
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            viewModel.move(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            val view = viewHolder?.itemView ?: return
            when (actionState) {
                ItemTouchHelper.ACTION_STATE_DRAG -> {
                    ViewCompat.animate(view).setDuration(150L).translationZ(pickUpElevation)

                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            ViewCompat.animate(viewHolder.itemView).setDuration(150L).translationZ(0f)

        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }
    }
}
