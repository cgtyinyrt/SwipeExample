package com.example.svaypexample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.svaypexample.databinding.RowLayoutBinding

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardHolder>() {

    private var initialX: Float = 0f
    private var initialY: Float = 0f
    private val SWIPE_THRESHOLD = 150
    private val interpolator = AccelerateDecelerateInterpolator()

    inner class CardHolder(
        private val binding: RowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val binding = RowLayoutBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
        return CardHolder(binding)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        setOnTouchListener(holder)
    }

    override fun getItemCount(): Int {
        return 5
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnTouchListener(holder: RecyclerView.ViewHolder) {
        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = motionEvent.x
                    initialY = motionEvent.y
                    true
                }

                MotionEvent.ACTION_UP -> {
                    val diffX = motionEvent.x - initialX
                    val diffY = motionEvent.y - initialY
                    if (kotlin.math.abs(diffX) > SWIPE_THRESHOLD && kotlin.math.abs(diffY) < SWIPE_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(holder.itemView)
                        } else {
                            onSwipeLeft(holder.itemView)
                        }
                    } else if (kotlin.math.abs(diffY) > SWIPE_THRESHOLD && kotlin.math.abs(diffX) < SWIPE_THRESHOLD && diffY < 0) {
                        onSwipeTop(holder.itemView)
                    }
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val diffX = motionEvent.x - initialX
                    val diffY = motionEvent.y - initialY
                    holder.itemView.translationX = diffX
                    holder.itemView.translationY = diffY
                    true
                }

                else -> false
            }
        }
    }

    private fun onSwipeLeft(itemView: View) {
        val parentView = itemView.parent as RecyclerView
        itemView.animate()
            .translationX((-parentView.height).toFloat())
            .rotation(-45f)
            .setInterpolator(interpolator)
            .setDuration(350)
            .withEndAction {
                parentView.removeView(itemView)
            }
            .start()
    }

    private fun onSwipeRight(itemView: View) {
        val parentView = itemView.parent as RecyclerView
        itemView.animate()
            .translationX(parentView.height.toFloat())
            .rotation(45f)
            .setInterpolator(interpolator)
            .setDuration(350)
            .withEndAction {
                parentView.removeView(itemView)
            }
            .start()
    }

    private fun onSwipeTop(itemView: View) {
        val parentView = itemView.parent as RecyclerView
        itemView.animate()
            .translationY((-parentView.height).toFloat())
            .setInterpolator(interpolator)
            .setDuration(350)
            .withEndAction {
                parentView.removeView(itemView)
            }
            .start()
    }
}