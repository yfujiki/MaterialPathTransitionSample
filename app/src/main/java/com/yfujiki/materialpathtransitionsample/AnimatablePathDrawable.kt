package com.yfujiki.materialpathtransitionsample

import android.animation.ValueAnimator
import android.content.Context
import java.lang.Math.pow
import java.lang.Math.sqrt
import java.util.*

class AnimatablePathDrawable(points: MutableList<NormalizedPoint>, context: Context): PathDrawable(points, context),
    ValueAnimator.AnimatorUpdateListener {

    private var startPoints: List<NormalizedPoint> = mutableListOf()
    private var endPoints: List<NormalizedPoint> = mutableListOf()

    private val animator: ValueAnimator = {
        val animator = ValueAnimator.ofInt(0, 1)
        animator.duration = 1000
        animator.addUpdateListener(this)
        animator
    }()

    fun startAnimating(toEndPoints: List<NormalizedPoint>) {
        configureStartEndPoints(points, toEndPoints)
        animator.start()
    }

    private fun configureStartEndPoints(fromStartPoints: List<NormalizedPoint>, toEndPoints: List<NormalizedPoint>) {
        val startPathLength = euclidianDistanceForPath(fromStartPoints)
        val endPathLength = euclidianDistanceForPath(toEndPoints)

        // Merged points when projecting both startPoints/endPoints into 1-dimensional line of (0,1)
        var projectedMergedPoints: MutableList<Double> = mutableListOf( 0.0 )

        (1 until fromStartPoints.size).forEach { i ->
            val pointsToI = (fromStartPoints.slice(0..i)).toList()
            val normalizedDistanceToI = euclidianDistanceForPath(pointsToI) / startPathLength
            projectedMergedPoints.add(normalizedDistanceToI)
        }
        (1 until toEndPoints.size).forEach { i ->
            val pointsToI = (toEndPoints.slice(0..i)).toList()
            val normalizedDistanceToI = euclidianDistanceForPath(pointsToI) / endPathLength
            projectedMergedPoints.add(normalizedDistanceToI)
        }

        projectedMergedPoints.sort()

        startPoints = mapProjectedPointsIntoPath(fromStartPoints, projectedMergedPoints)
        endPoints = mapProjectedPointsIntoPath(toEndPoints, projectedMergedPoints)
    }

    private fun euclidianDistanceForPath(points: List<NormalizedPoint>): Double {
        if (points.size < 1) {
            return 0.0
        }

        val shiftedPoints = points.slice(1 until points.size)
        return points.zip(shiftedPoints).sumByDouble { (a, b) ->
            sqrt(pow((b.y - a.y).toDouble(), 2.0) + pow((b.x - a.x).toDouble(), 2.0))
        }
    }

    private fun mapProjectedPointsIntoPath(path: List<NormalizedPoint>,
                                           projectedPoints: List<Double>): List<NormalizedPoint> {

        val entireDistance = euclidianDistanceForPath(path)

        val distanceKeyedPoints: SortedMap<Double, NormalizedPoint> = sortedMapOf()
        var distancesToPoints: SortedSet<Double> = sortedSetOf()
        path.forEachIndexed { index, normalizedPoint ->
            if (index == 0) {
                distanceKeyedPoints[0.0] = normalizedPoint
                distancesToPoints.add(0.0)
            } else if (index == path.size - 1) {
                distanceKeyedPoints[entireDistance] = normalizedPoint
                distancesToPoints.add(entireDistance)
            } else {
                val pointsToIndex = (path.slice(0..index)).toList()
                val distanceToIndex = euclidianDistanceForPath(pointsToIndex)
                distanceKeyedPoints[distanceToIndex] = normalizedPoint
                distancesToPoints.add(distanceToIndex)
            }
        }


        var tempPoints: MutableList<NormalizedPoint> = mutableListOf()

        projectedPoints.forEach { projectedPoint ->
            val distanceToProjectedPoint = projectedPoint * entireDistance
            val pointInPath = pointInPath(distanceKeyedPoints, distanceToProjectedPoint)
            tempPoints.add(pointInPath)
        }

        return tempPoints.toList()
    }

    private fun pointInPath(distanceKeyedPoints: SortedMap<Double, NormalizedPoint>, distanceToPoint: Double) : NormalizedPoint {
        var prevDistance = distanceKeyedPoints.firstKey()
        var prevPoint = distanceKeyedPoints[prevDistance]!!

        for ((distance, point) in distanceKeyedPoints) {
            if (distance == distanceToPoint) {
                return point
            } else if ((distanceToPoint - prevDistance) * (distanceToPoint - distance) < 0) {

                val x = (point.x - prevPoint.x) * (distanceToPoint - prevDistance) / (distance - prevDistance) + prevPoint.x
                val y = (point.y - prevPoint.y) * (distanceToPoint - prevDistance) / (distance - prevDistance) + prevPoint.y
                return NormalizedPoint(x.toFloat(), y.toFloat())
            }

            prevDistance = distance
            prevPoint = point
        }

        return distanceKeyedPoints[distanceKeyedPoints.lastKey()]!!
    }

    override fun onAnimationUpdate(animator: ValueAnimator) {
        val fraction = animator.animatedFraction

        points.clear()

        startPoints.forEachIndexed({ index, startPoint ->
            val endPoint = endPoints[index]

            val x = (endPoint.x - startPoint.x) * fraction + startPoint.x
            val y = (endPoint.y - startPoint.y) * fraction + startPoint.y

            points.add(NormalizedPoint(x, y))
        })

        invalidateSelf()
    }
}