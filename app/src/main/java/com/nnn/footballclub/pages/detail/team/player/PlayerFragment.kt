package com.nnn.footballclub.pages.detail.team.player

import com.nnn.footballclub.model.Team
import com.nnn.footballclub.pages.detail.team.TeamDetailContract


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

class PlayerFragment : TeamDetailContract._PlayerView(){

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