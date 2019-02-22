package com.yfujiki.materialpathtransitionsample

import android.transition.Fade

val INITIAL_PATH = listOf(
    NormalizedPoint(0.2f, 0.5f),
    NormalizedPoint(0.8f, 0.5f)
)

val FINAL_PATH = listOf(
    NormalizedPoint(0.1f, 0.74f),
    NormalizedPoint(0.15f, 0.68f),
    NormalizedPoint(0.26f, 0.69f),
    NormalizedPoint(0.27f, 0.72f),
    NormalizedPoint(0.29f, 0.7f),
    NormalizedPoint(0.35f, 0.72f),
    NormalizedPoint(0.45f, 0.72f),
    NormalizedPoint(0.5f, 0.66f),
    NormalizedPoint(0.7f, 0.6f),
    NormalizedPoint(0.72f, 0.5f),
    NormalizedPoint(0.8f, 0.4f)
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
