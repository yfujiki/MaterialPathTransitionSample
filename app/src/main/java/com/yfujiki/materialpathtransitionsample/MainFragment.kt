package com.yfujiki.materialpathtransitionsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*

interface MainFragmentListener {
    fun mapButtonClicked()
}

@SuppressLint("ValidFragment")
class MainFragment(val listener: MainFragmentListener, val firstTime: Boolean = false): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val path: MutableList<NormalizedPoint> = if (firstTime) INITIAL_PATH.toMutableList() else FINAL_PATH.toMutableList()
        val drawable = AnimatablePathDrawable(path, activity!!.applicationContext)
        view.pathCanvas.background = drawable

        drawable.startAnimating(INITIAL_PATH)

        view.button.setOnClickListener({
            listener.mapButtonClicked()
        })

        return view
    }
}