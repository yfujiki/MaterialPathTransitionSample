package com.yfujiki.materialpathtransitionsample

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

class PathDrawable(val context: Context): Drawable() {
    private val path: Path = {
        val p = Path()
        p
    }()

    private val paint: Paint = {
        val p = Paint()
        p.color = ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        p.strokeWidth = 20.0f
        p.style = Paint.Style.STROKE
        p.strokeCap = Paint.Cap.ROUND

        p
    }()

    private var startPath = listOf(
        Pair(0.2f, 0.5f),
        Pair(0.8f, 0.5f)
    )

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

    private fun initializePath() {
        path.reset()

        val b = bounds

        startPath.forEachIndexed { index, startPoint ->
            val x = startPoint.first
            val y = startPoint.second

            if (index == 0) {
                path.moveTo(x * b.width(), y * b.height())
            } else {
                path.lineTo(x * b.width(), y * b.height())
            }
        }
    }
}