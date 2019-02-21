package com.yfujiki.materialpathtransitionsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_main.*
import android.os.Build
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet


class MainActivity : AppCompatActivity(), MainFragmentListener, MapFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            fragment = MainFragment(this)
            fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    override fun mapButtonClicked() {

        println("Map button clicked")
        val fragmentManager = supportFragmentManager
        val fragment = MapFragment(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val details = TransitionSet()
            details.addTransition(ChangeBounds())
            fragment.sharedElementEnterTransition = details
        }

        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addSharedElement(pathCanvas, getString(R.string.path_canvas_transition))
            .commit()
    }

    override fun closeButtonClicked() {
        println("Close button clicked")
        val fragmentManager = supportFragmentManager
        val fragment = MainFragment(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val details = TransitionSet()
            details.addTransition(ChangeBounds())
            fragment.sharedElementEnterTransition = details
        }

        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
