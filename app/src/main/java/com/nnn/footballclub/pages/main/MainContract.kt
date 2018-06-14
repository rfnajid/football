package com.nnn.footballclub.pages.main

import android.support.v4.app.Fragment
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.main.match.MatchItemAdapter
import com.nnn.footballclub.pages.main.team.TeamItemAdapter
import com.nnn.footballclub.utils.base.BaseListFragment
import com.nnn.footballclub.utils.base.BaseListPresenter
import com.nnn.footballclub.utils.base.BasePresenter
import com.nnn.footballclub.utils.base.BaseView


/**
 * Created by ridhaaaaazis on 18/05/18.
 */
interface MainContract{

    interface _MainView : BaseView<_MainPresenter> {
        fun selectFragment(fragment : Fragment)
        fun hideSearchButton(hide : Boolean)
        fun changeTitle(newTitle : String)
    }

    interface _MainPresenter : BasePresenter {
        var type : MainPresenter.TYPE
        fun loadFragment(id : Int)
    }

    abstract class _MatchListView : BaseListFragment<Event,MatchItemAdapter>()

    abstract class _MatchListPresenter : BaseListPresenter<Event, MatchItemAdapter>(){
        abstract fun loadFavorite()
        abstract fun loadEvent(response : EventResponse)
        abstract fun loadTeam(response: TeamResponse, index: Int, isHome : Boolean)
    }

    abstract class _TeamListView : BaseListFragment<Team,TeamItemAdapter>()

    abstract class _TeamListPresenter : BaseListPresenter<Team, TeamItemAdapter>()


}