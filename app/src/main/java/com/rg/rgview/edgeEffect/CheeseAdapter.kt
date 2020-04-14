package com.rg.rgview.edgeEffect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import com.rg.rgview.R
import com.rg.rgview.reoder.CheeseViewHolder
import com.rg.rgview.widget.logr

/**
 * Created by roger on 2020/4/14
 */
class CheeseAdapter(private val data: List<String>) : RecyclerView.Adapter<CheeseAdapter.CheeseHolder>() {


    class CheeseHolder(view: View) : RecyclerView.ViewHolder(view) {
        val translationY: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
                .setSpring(
                        SpringForce()
                                .setFinalPosition(0f)
                                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                                .setStiffness(SpringForce.STIFFNESS_LOW)
                )

        companion object {
            fun from(parent: ViewGroup): CheeseHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycle_item_cheese, parent, false)
                return CheeseHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseHolder {
        return CheeseHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CheeseHolder, position: Int) {

    }

    val edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
        override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
            return object : EdgeEffect(view.context) {
                override fun onPull(deltaDistance: Float) {
                    super.onPull(deltaDistance)
                    handlePull(deltaDistance)
                }

                override fun onPull(deltaDistance: Float, displacement: Float) {
                    super.onPull(deltaDistance, displacement)
                    handlePull(deltaDistance)
                }

                private fun handlePull(deltaDistance: Float) {
                    val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                    val translationYDelta = sign * view.height *  deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                    view.forEachVisibleHolder { holder: CheeseHolder ->
                        holder.translationY.cancel()
                        holder.itemView.translationY += translationYDelta

                    }
                }

                override fun onRelease() {
                    super.onRelease()
                    view.forEachVisibleHolder { holder: CheeseHolder ->
                        holder.translationY.start()
                    }
                }

                override fun onAbsorb(velocity: Int) {
                    super.onAbsorb(velocity)
                    val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                    val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                    view.forEachVisibleHolder { holder: CheeseHolder ->
                        holder.translationY.setStartVelocity(translationVelocity).start()
                    }
                }
            }
        }
    }
}

/**
 * Runs [action] on every visible [RecyclerView.ViewHolder] in this [RecyclerView].
 */
private inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
        action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}


/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f
/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f