package com.nnn.footballclub.pages.main

import android.content.Context
import android.support.v4.app.Fragment
import com.nnn.footballclub.R


/**
 * Created by ridhaaaaazis on 18/05/18.
 */

class MainPresenter(view : MainContract.MainView) : MainContract.MainPresenter{

    val view = view

    override fun start(context:Context) {
        navMenuSelected(R.id.navigation_past)
    }

    override fun navMenuSelected(id: Int) {
        var fragment :Fragment?= null

        when(id){
            R.id.navigation_past -> {
                fragment = ListFragment.create(ListPresenter.TYPE.PAST)
            }
            R.id.navigation_next -> {
                fragment = ListFragment.create(ListPresenter.TYPE.NEXT)
            }
            R.id.navigation_favorite -> {
                fragment = ListFragment.create(ListPresenter.TYPE.FAVORITE)
            }
        }

        view.selectFragment(fragment as Fragment)
    }
}