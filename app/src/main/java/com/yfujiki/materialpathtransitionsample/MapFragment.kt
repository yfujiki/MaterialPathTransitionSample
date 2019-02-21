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

        view.pathCanvas.background = PathDrawable(activity!!.applicationContext)

        view.button.setOnClickListener({
            listener.closeButtonClicked()
        })

        return view
    }
}