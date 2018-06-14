package com.nnn.footballclub.pages.detail.team.player

import android.content.Context
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.responses.PlayerResponse
import com.nnn.footballclub.pages.detail.team.TeamDetailContract
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


/**
 * Created by ridhaaaaazis on 21/05/18.
 */

open class PlayerListPresenter(
        val view : TeamDetailContract._PlayerView,
        override var coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) : TeamDetailContract._PlayerPresenter() {

    override lateinit var adapter: PlayerItemAdapter

    internal lateinit var context : Context

    override lateinit var team : Team

    override fun start(context : Context) {
        adapter = PlayerItemAdapter(context,data)
    }

    override fun onResume() {
        //Nothing
    }

    override fun loadData() {

        Global.log("load data player list")

        lateinit var req: String

        data.clear()

        req = SportsDBApiAnko.getPlayers(team.id)

        async(coroutineContext.main) {
            val data = bg {
                Global.gson.fromJson(SportsDBApiAnko
                        .doRequest(req),
                        PlayerResponse::class.java
                )
            }

            Global.log("ASYNC PLAYER LIST")

            data.await()
            Global.log("done await")
            loadToView(data.getCompleted())
        }
    }

    internal fun loadToView(response : PlayerResponse){

        Global.log("player load to view")

        if(Global.nullOrEmpty(response.players)){
            view.empty()
            Global.log("player null")
        }else {
            view.loading(false)
            data.addAll(response.players)
            Global.log("player not null : ${data.size}")
        }
        adapter.notifyDataSetChanged()
    }
}