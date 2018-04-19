package com.nnn.footballclub.layout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nnn.footballclub.activity.MainActivity
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout


/**
 * Created by ridhaaaaazis on 19/04/18.
 */

class MainActivityUI(val listAdapter: RecyclerViewAdapter) : AnkoComponent<MainActivity> {

    lateinit var list : RecyclerView

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            list = recyclerView {
                val orientation = LinearLayoutManager.VERTICAL
                layoutManager = LinearLayoutManager(context, orientation, true)
                overScrollMode = View.OVER_SCROLL_NEVER
                adapter = listAdapter
            }
        }
    }
}
