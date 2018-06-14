package com.nnn.footballclub.pages.detail.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Team
import kotlinx.android.synthetic.main.fragment_overview.view.*


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

class OverviewFragment : Fragment(){

    lateinit var team : Team
    companion object {
        fun create(team : Team) : OverviewFragment{
            val fragment = OverviewFragment()
            fragment.team=team
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_overview,container,false)

        prepareView(view)

        return view
    }


    private fun prepareView(view : View){
        view.textDesc.text      = team.description
        view.textAlias.text     = team.alias
        view.textCity.text      = team.city()
        view.textFounded.text   = team.formedYear.toString()
        view.textLeague.text    = team.league
        view.textStadium.text   = team.stadium
        view.textManager.text   = team.manager
    }
}