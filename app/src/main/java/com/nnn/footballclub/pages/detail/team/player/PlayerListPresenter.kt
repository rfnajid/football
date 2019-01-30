package com.nnn.footballclub.pages.detail.team.player

import android.content.Context
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.responses.PlayerResponse
import com.nnn.footballclub.pages.detail.team.TeamDetailContract
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by ridhaaaaazis on 21/05/18.
 */

open class PlayerListPresenter(
        val view : TeamDetailContract._PlayerView,
        var coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) : TeamDetailContract._PlayerPresenter() {

    internal lateinit var context : Context

    override lateinit var team : Team

    override fun start(context : Context) {}

    override fun onResume() {
        //Nothing
    }

    override fun loadData() {

        Global.log("load data player list")

        lateinit var req: String

        view.data.clear()

        req = SportsDBApiAnko.getPlayers(team.id)

        GlobalScope.launch(coroutineContext.main) {
            val data = Global.gson.fromJson(SportsDBApiAnko
                    .doRequest(req).await(),
                    PlayerResponse::class.java
            )

            Global.log("ASYNC PLAYER LIST")

            loadToView(data)
        }
    }

    internal fun loadToView(response : PlayerResponse){

        Global.log("player load to view")

        if(Global.nullOrEmpty(response.players)){
            view.empty()
            Global.log("player null")
        }else {
            view.loading(false)
            view.data.addAll(response.players)
            Global.log("player not null : ${view.data.size}")
        }
        view.adapter.notifyDataSetChanged()
    }
}