package com.nnn.footballclub.pages.main

import android.content.Context
import android.support.v4.app.Fragment
import com.nnn.footballclub.R
import com.nnn.footballclub.pages.main.favorite.FavoriteFragment
import com.nnn.footballclub.pages.main.match.MatchFragment
import com.nnn.footballclub.pages.main.team.TeamFragment


/**
 * Created by ridhaaaaazis on 18/05/18.
 */

class MainPresenter(val view : MainContract._MainView) : MainContract._MainPresenter
{

    enum class TYPE{
        TEAM, MATCH
    }

    override lateinit var type : TYPE

    override fun start(context:Context) {
        loadFragment(R.id.navigation_team)
    }

    override fun loadFragment(id: Int) {
        var fragment :Fragment?= null
        var title : String = ""

        when(id){
            R.id.navigation_match -> {
                fragment = MatchFragment.create()
                type = TYPE.MATCH
                title = "Match"
                view.hideSearchButton(false)
            }
            R.id.navigation_team -> {
                fragment = TeamFragment.create()
                type = TYPE.TEAM
                title = "Team"
                view.hideSearchButton(false)
            }
            R.id.navigation_favorite -> {
                fragment = FavoriteFragment.create()
                title = "Favorite"
                view.hideSearchButton(true)
            }
        }

        view.changeTitle(title)
        view.selectFragment(fragment as Fragment)
    }
}