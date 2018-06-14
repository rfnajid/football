package com.nnn.footballclub.pages.main.team

import android.content.Context
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.db.FavoriteTeamDB
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.main.MainContract
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


/**
 * Created by ridhaaaaazis on 29/05/18.
 */

open class TeamListPresenter(
        private val view : MainContract._TeamListView,
        override var coroutineContext : CoroutineContextProvider = CoroutineContextProvider()
) : MainContract._TeamListPresenter(){

    enum class TYPE {NORMAL,FAVORITE,SEARCH}

    override lateinit var adapter: TeamItemAdapter

    internal lateinit var favoriteTeamDB : FavoriteTeamDB

    lateinit var type : TYPE
    var idLeague : Int = 0
    lateinit var query : String
    
    override fun start(context: Context) {
        start(TeamItemAdapter(context,data),FavoriteTeamDB(context))
    }

    fun start(adapter: TeamItemAdapter,favoriteTeamDB: FavoriteTeamDB){ //for testing purpose
        this.adapter=adapter
        this.favoriteTeamDB=favoriteTeamDB
    }

    override fun onResume() {
        if(type== TYPE.FAVORITE) {
            var list = favoriteTeamDB.getAll()
            if(list.size!=data.size)
                loadFavorite()
        }
    }


    override fun loadData() {

        Global.log("load data Team")


        lateinit var req : String

        when(type){
            TYPE.FAVORITE -> {
                view.empty()
                return
            }
            TYPE.NORMAL -> {
                req = SportsDBApiAnko.getTeams(idLeague.toLong())
            }
            TYPE.SEARCH -> {
                req = SportsDBApiAnko.searchTeam(query)
            }
        }


        async(coroutineContext.main) {
            val data = bg {
                Global.gson.fromJson(SportsDBApiAnko
                        .doRequest(req),
                        TeamResponse::class.java
                )
            }

            data.await()
            loadTeam(data.getCompleted())
        }
    }

    fun loadTeam(response: TeamResponse) {

        data.clear()

        if(Global.nullOrEmpty(response.teams)){
            Global.log("load team : empty ")
            view.empty()
        }else{
            Global.log("load team : not empty ")
            data.addAll(response.teams)
            view.loading(false)
        }

        adapter.notifyDataSetChanged()
    }

    fun loadFavorite() {

        val list = favoriteTeamDB.getAll()

        Global.log("Fav Team From DB (${list.size}), empty ? ${list.isEmpty()}")

        if(list.isEmpty()) {
            view.empty()
            return
        }

        data.clear()

        for ( f in list){
            data.add(Team.copy(f))
        }

        view.loading(false)

        adapter.notifyDataSetChanged()
    }

}