package com.yfujiki.materialpathtransitionsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager

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
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun closeButtonClicked() {
        println("Close button clicked")
        val fragmentManager = supportFragmentManager
        val fragment = MainFragment(this)
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
