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
class MainFragment(val listener: MainFragmentListener): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val drawable = AnimatablePathDrawable(INITIAL_PATH.toMutableList(), activity!!.applicationContext)
        view.pathCanvas.background = drawable

        view.button.setOnClickListener({
            listener.mapButtonClicked()
        })

        return view
    }
}