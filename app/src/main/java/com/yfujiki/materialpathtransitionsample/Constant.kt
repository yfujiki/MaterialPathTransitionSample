package com.yfujiki.materialpathtransitionsample

import android.transition.Fade

val INITIAL_PATH = listOf(
    NormalizedPoint(0.2f, 0.5f),
    NormalizedPoint(0.8f, 0.5f)
)

val FADE_OUT_TRANSITION = {
    val fade = Fade()
    fade.duration = 200
    fade
}()

val FADE_IN_TRANSITION = {
    val fade = Fade()
    fade.duration = 1000
    fade
}()