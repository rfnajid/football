package com.nnn.footballclub.pages.detail.team

import com.nnn.footballclub.model.Player
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.pages.detail.team.player.PlayerItemAdapter
import com.nnn.footballclub.utils.base.BaseDetailContract
import com.nnn.footballclub.utils.base.BaseListFragment
import com.nnn.footballclub.utils.base.BaseListPresenter


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

interface TeamDetailContract : BaseDetailContract{


    abstract class _PlayerView : BaseListFragment<Player,PlayerItemAdapter>()

    abstract class _PlayerPresenter : BaseListPresenter {
        abstract var team : Team
    }
}