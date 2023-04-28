package com.example.svaypexample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.svaypexample.databinding.RowLayoutBinding
import java.lang.Math.abs

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardHolder>() {

    private var initialX: Float = 0f
    private var initialY: Float = 0f
    private val SWIPE_THRESHOLD = 150f
//    private val interpolator = AccelerateDecelerateInterpolator()
    private val interpolator = DecelerateInterpolator()
//    var isSwiped = false

    inner class CardHolder(
        val binding: RowLayoutBinding
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        //holder.bind(items[position])
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
//                    } else {
//                        if(isSwiped){
//                            isSwiped = false
//                            animateCardViewToInitialPosition(holder.itemView)
//                        }
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

    override fun getItemCount(): Int {
        return 5
    }

    private fun onSwipeLeft(itemView: View) {
//        isSwiped = true
        val parentView = itemView.parent as RecyclerView

        itemView.animate()
            .translationX((-parentView.height).toFloat())
            .rotation(-45f)
            .setInterpolator(interpolator)
            .setDuration(350)
            .withEndAction {
                // Remove the card from the parent view when animation is finished
                parentView.removeView(itemView)
            }
            .start()
    }

    private fun onSwipeRight(itemView: View) {
//        isSwiped = true
        val parentView = itemView.parent as RecyclerView

        itemView.animate()
            .translationX(parentView.height.toFloat())
            .rotation(45f)
            .setInterpolator(interpolator)
            .setDuration(350)
            .withEndAction {
                // Remove the card from the parent view when animation is finished
                parentView.removeView(itemView)
            }
            .start()
    }

    private fun onSwipeTop(itemView: View) {
//        isSwiped = true
        val parentView = itemView.parent as RecyclerView

        itemView.animate()
            .translationY((-parentView.height).toFloat())
            .setInterpolator(interpolator)
            .setDuration(350)
            .withEndAction {
                // Remove the card from the parent view when animation is finished
                parentView.removeView(itemView)
            }
            .start()
    }

//    private fun animateCardViewToInitialPosition(itemView: View) {
//        itemView.animate()
//            .translationX(0f)
//            .translationY(0f)
//            .rotation(0f)
//            .setInterpolator(interpolator)
//            .setDuration(350)
//            .start()
//    }
}