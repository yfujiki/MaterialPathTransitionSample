package com.yfujiki.materialpathtransitionsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*

interface MapFragmentListener {
    fun closeButtonClicked()
}

@SuppressLint("ValidFragment")
class MapFragment(val listener: MapFragmentListener): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        var drawable = AnimatablePathDrawable(INITIAL_PATH.toMutableList(), activity!!.applicationContext)
        view.pathCanvas.background = drawable

        drawable.startAnimating(listOf(
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
        ))

        view.button.setOnClickListener({
            drawable.startAnimating(INITIAL_PATH)
            listener.closeButtonClicked()
        })

        return view
    }
}