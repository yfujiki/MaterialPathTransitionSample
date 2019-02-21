package com.yfujiki.materialpathtransitionsample

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import kotlin.math.ceil

class AnimatablePathDrawable(points: MutableList<NormalizedPoint>, context: Context): PathDrawable(points, context),
    ValueAnimator.AnimatorUpdateListener {

    private val startPoints: MutableList<NormalizedPoint> = mutableListOf()
    private val endPoints: MutableList<NormalizedPoint> = mutableListOf()

    private val animator: ValueAnimator = {
        val animator = ValueAnimator.ofInt(0, 1)
        animator!!.duration = 1000
        animator!!.addUpdateListener(this)
        animator!!
    }()

    fun startAnimating(toEndPoints: List<NormalizedPoint>) {
        configureStartEndPoints(points, toEndPoints)
        animator.start()
    }

    private fun configureStartEndPoints(fromStartPoints: List<NormalizedPoint>, toEndPoints: List<NormalizedPoint>) {
        val startLeft = fromStartPoints.first().x
        val endLeft = toEndPoints.first().x

        val startRight = fromStartPoints.last().x
        val endRight = toEndPoints.last().x

        val scaleFactor = (endRight - endLeft) / (startRight - startLeft)

        startPoints.clear()
        startPoints.addAll(fromStartPoints)

        endPoints.clear()
        endPoints.addAll(toEndPoints)

        fromStartPoints.forEach {
            val correspondingEndPointX = endLeft + (it.x - startLeft) * scaleFactor
            val correspondingEndPointY = findInterpolatedYIn(toEndPoints, correspondingEndPointX)
            endPoints.add(NormalizedPoint(correspondingEndPointX, correspondingEndPointY))
        }

        toEndPoints.forEach {
            val correspondingStartPointX = startLeft + (it.x - endLeft) * scaleFactor
            val correspondingStartPointY = findInterpolatedYIn(fromStartPoints, correspondingStartPointX)
            startPoints.add(NormalizedPoint(correspondingStartPointX, correspondingStartPointY))
        }

        startPoints.sortBy { it.x }
        endPoints.sortBy { it.x }
    }

    private fun findInterpolatedYIn(points: List<NormalizedPoint>, x: Float) : Float {

        points.forEachIndexed { index, point ->
            if (point.x == x) {
                return point.y
            } else if (point.x > x) {
                return (point.y - points[index - 1].y) * (x - points[index - 1].x) / (point.x - points[index - 1].x)
            }
        }

        return points.last().y
    }

    override fun onAnimationUpdate(animator: ValueAnimator) {
        val fraction = animator.animatedFraction

        points.clear()
        path.reset()

        startPoints.forEachIndexed({ index, startPoint ->
            val endPoint = endPoints[index]

            val x = (endPoint.x - startPoint.x) * fraction + startPoint.x
            val y = (endPoint.y - startPoint.y) * fraction + startPoint.y

            points.add(NormalizedPoint(x, y))

            val b = bounds
            if (index == 0) {
                path.moveTo(x * b.width(), y * b.height())
            } else {
                path.lineTo(x * b.width(), y * b.height())
            }
        })

        invalidateSelf()
    }
}