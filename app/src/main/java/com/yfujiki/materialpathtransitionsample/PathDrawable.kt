package com.yfujiki.materialpathtransitionsample

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

data class NormalizedPoint(val x: Float, val y: Float)

open class PathDrawable(val points: MutableList<NormalizedPoint>, val context: Context) : Drawable() {

    protected val path: Path = {
        val p = Path()
        p
    }()

    protected val paint: Paint = {
        val p = Paint()
        p.color = ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        p.strokeWidth = 20.0f
        p.style = Paint.Style.STROKE
        p.strokeCap = Paint.Cap.ROUND

        p
    }()

    init {
    }

    override fun draw(canvas: Canvas) {
        initializePath()
        canvas.drawPath(path, paint)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    protected fun initializePath() {
        path.reset()

        val b = bounds

        points.forEachIndexed { index, point ->
            val x = point.x
            val y = point.y

            if (index == 0) {
                path.moveTo(x * b.width(), y * b.height())
            } else {
                path.lineTo(x * b.width(), y * b.height())
            }
        }
    }
}