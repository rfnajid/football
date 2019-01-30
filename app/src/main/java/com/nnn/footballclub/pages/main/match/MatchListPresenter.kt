package com.nnn.footballclub.pages.main.match

import android.content.Context
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteEventDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.main.MainContract
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by ridhaaaaazis on 21/05/18.
 */

open class MatchListPresenter(
        val view : MainContract._MatchListView,
        var coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) : MainContract._MatchListPresenter() {

    enum class TYPE {
        PAST,NEXT,FAVORITE,SEARCH
    }

    internal lateinit var favoriteEventDB: FavoriteEventDB

    internal lateinit var context : Context

    lateinit var type : TYPE
    var idLeague : Int = 0
    lateinit var query : String

    override fun start(context : Context) {
        favoriteEventDB = FavoriteEventDB(context)
    }

    override fun onResume() {
        if(type== TYPE.FAVORITE) {
            var list = favoriteEventDB.getAll()
            if(list.size!=view.data.size)
                loadFavorite()
        }
    }

    override fun loadTeam(response: TeamResponse, i : Int, isHome: Boolean) {
        val team = response.teams[0]
        val d = view.data.get(i)

        if(isHome){
            d.homeTeam=team
        }else {
            d.awayTeam = team
        }
        view.data.set(i,d)
        Global.log("Team : ${view.data.get(i)}")
    }

    private fun checkLoadEvent(i : Int){
        Global.log("checkLoadEvent -> i : ${i}")
        if(i>=view.data.size-1){
            Global.log("GET TEAM DETAIL ALL COMPLETED : size -> ${view.data.size}")
            view.adapter.notifyDataSetChanged()
            view.loading(false)
        }
    }

    override fun loadData() {

        lateinit var req: String

        Global.log("TTYPE : ${type}")

        when (type) {
            TYPE.NEXT -> {
                req = SportsDBApiAnko.getNext(idLeague.toLong())
            }
            TYPE.PAST -> {
                req = SportsDBApiAnko.getPast(idLeague.toLong())
            }
            TYPE.FAVORITE -> {
                view.empty()
                return
            }
            TYPE.SEARCH -> {
                req = SportsDBApiAnko.searchMatch(query)
            }
        }

        GlobalScope.launch(coroutineContext.main){
                val data = Global.gson.fromJson(SportsDBApiAnko
                        .doRequest(req).await(),
                        EventResponse::class.java)

                loadEvent(data)
        }
    }

    override fun loadFavorite() {

        val list = favoriteEventDB.getAll()

        Global.log("Fav From DB (${list.size}")

        if(list.isEmpty()) {
            view.empty()
            return
        }

        view.data.clear()

        for ( f in list){
            view.data.add(Event.copy(f))
        }

        view.loading(false)

        view.adapter.notifyDataSetChanged()
    }

    override fun loadEvent(response: EventResponse) {


        if(Global.nullOrEmpty(response.events)) {
            view.empty()
            view.adapter.notifyDataSetChanged()
            return
        }

        view.data.addAll(response.events)

        for(i in 0..(view.data.size-1)){
            val d = view.data.get(i)
            Global.log("EVENT : ${d.name}")

            GlobalScope.launch (coroutineContext.main){
                val data = Global.gson.fromJson(SportsDBApiAnko
                        .doRequest(SportsDBApiAnko.getTeam(d.homeId)).await(),
                        TeamResponse::class.java)

                loadTeam(data, i, true)


                launch(coroutineContext) {
                    val data = Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getTeam(d.awayId)).await(),
                            TeamResponse::class.java)
                    loadTeam(data, i, false)
                    checkLoadEvent(i)
                }
            }
        }
    }
}