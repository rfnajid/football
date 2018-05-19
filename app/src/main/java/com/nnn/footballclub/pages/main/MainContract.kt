package com.nnn.footballclub.pages.main

import android.support.v4.app.Fragment
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.base.BasePresenter
import com.nnn.footballclub.utils.base.BaseView


/**
 * Created by ridhaaaaazis on 18/05/18.
 */
interface MainContract{

    interface MainPresenter : BasePresenter {
        fun navMenuSelected(id : Int)
    }

    interface ListPresenter : BasePresenter{
        fun onResume()
        fun loadData()
        fun loadFavorite(list : List<Long>)
        fun loadEvent(response : EventResponse, isFavorite : Boolean=false)
        fun loadTeam(response: TeamResponse, event: Event, isHome : Boolean)
    }

    interface MainView : BaseView<MainPresenter> {
        fun selectFragment(fragment : Fragment)
    }

    interface ListView : BaseView<ListPresenter>{
        fun empty(bool : Boolean)
        var type : com.nnn.footballclub.pages.main.ListPresenter.TYPE
    }


}