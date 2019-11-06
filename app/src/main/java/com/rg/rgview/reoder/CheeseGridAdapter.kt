package com.rg.rgview.reoder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rg.rgview.R

/**
 * Create by roger
 * on 2019/11/5
 */
class CheeseGridAdapter(
        private val onItemLongClick: (holder: RecyclerView.ViewHolder) -> Unit
) : ListAdapter<Cheese, CheeseViewHolder>(Cheese.DIFF_CALLBACK) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        return CheeseViewHolder(parent).apply {
            itemView.setOnLongClickListener {
                onItemLongClick(this)
                true
            }
        }
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class CheeseViewHolder(
        parent: ViewGroup
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_item_cheese_staggered_grid, parent, false)
) {
    private val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.cheese)
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val constraintSet = ConstraintSet().apply { clone(constraintLayout) }

    fun bind(cheese: Cheese) {
        constraintSet.setDimensionRatio(R.id.image, "H,${cheese.imageWidth}:${cheese.imageHeight}")
        constraintSet.applyTo(constraintLayout)

        Glide.with(image).load(cheese.image).into(image)
        name.text = cheese.name
    }
}