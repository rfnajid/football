package com.nnn.footballclub.pages.detail.team.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.pages.detail.team.TeamDetailContract


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

class PlayerFragment : TeamDetailContract._PlayerView(){

    override lateinit var adapter: PlayerItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = PlayerItemAdapter(context!!,data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun create(team : Team) : PlayerFragment {
            val fragment = PlayerFragment()
            val presenter = PlayerListPresenter(fragment as TeamDetailContract._PlayerView)
            presenter.team = team
            fragment.setPresenter(presenter)
            return fragment
        }
    }
}