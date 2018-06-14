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
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


/**
 * Created by ridhaaaaazis on 21/05/18.
 */

open class MatchListPresenter(
        val view : MainContract._MatchListView,
        override var coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) : MainContract._MatchListPresenter() {

    enum class TYPE {
        PAST,NEXT,FAVORITE,SEARCH
    }

    override lateinit var adapter: MatchItemAdapter

    internal lateinit var favoriteEventDB: FavoriteEventDB

    internal lateinit var context : Context

    lateinit var type : TYPE
    var idLeague : Int = 0
    lateinit var query : String

    override fun start(context : Context) {
        adapter = MatchItemAdapter(context, data)
        favoriteEventDB = FavoriteEventDB(context)
    }

    override fun onResume() {
        if(type== TYPE.FAVORITE) {
            var list = favoriteEventDB.getAll()
            if(list.size!=data.size)
                loadFavorite()
        }
    }

    override fun loadTeam(response: TeamResponse, i : Int, isHome: Boolean) {
        val team = response.teams[0]
        val d = data.get(i)

        if(isHome){
            d.homeTeam=team
        }else {
            d.awayTeam = team
        }
        data.set(i,d)
        Global.log("Team : "+data.get(i))
    }

    fun checkLoadEvent(i : Int){
        Global.log("checkLoadEvent -> i : ${i}")
        if(i>=data.size-1){
            Global.log("GET TEAM DETAIL ALL COMPLETED : size -> ${data.size}")
            adapter.notifyDataSetChanged()
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

        async(coroutineContext.main) {
            val data = bg {
                Global.gson.fromJson(SportsDBApiAnko
                        .doRequest(req),
                        EventResponse::class.java
                )
            }

            data.await()
            loadEvent(data.getCompleted())

        }
    }

    override fun loadFavorite() {

        val list = favoriteEventDB.getAll()

        Global.log("Fav From DB (${list.size}")

        if(list.isEmpty()) {
            view.empty()
            return
        }

        data.clear()

        for ( f in list){
            data.add(Event.copy(f))
        }

        view.loading(false)

        adapter.notifyDataSetChanged()
    }

    override fun loadEvent(response: EventResponse) {


        if(Global.nullOrEmpty(response.events)) {
            view.empty()
            adapter.notifyDataSetChanged()
            return
        }

        data.addAll(response.events)

        for(i in 0..(data.size-1)){
            val d = data.get(i)
            Global.log("EVENT : ${d.name}")

            async(coroutineContext.main){
                val data = bg {
                    Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getTeam(d.homeId)),
                            TeamResponse::class.java
                    )
                }

                data.await()

                loadTeam(data.getCompleted(),i,true)


                async(coroutineContext) {
                    val data = bg {
                        Global.gson.fromJson(SportsDBApiAnko
                                .doRequest(SportsDBApiAnko.getTeam(d.awayId)),
                                TeamResponse::class.java
                        )
                    }

                    data.await()

                    loadTeam(data.getCompleted(), i, false)
                    checkLoadEvent(i)
                }

            }
        }
    }
}