package com.nnn.footballclub.pages.main.team

import android.content.Context
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.db.FavoriteTeamDB
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.main.MainContract
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by ridhaaaaazis on 29/05/18.
 */

open class TeamListPresenter(
        private val view : MainContract._TeamListView,
        var coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) : MainContract._TeamListPresenter() {

    enum class TYPE {NORMAL,FAVORITE,SEARCH}

    internal lateinit var favoriteTeamDB : FavoriteTeamDB

    lateinit var type : TYPE
    var idLeague : Int = 0
    lateinit var query : String
    
    override fun start(context: Context) {
        start(FavoriteTeamDB(context))
    }

    fun start(favoriteTeamDB: FavoriteTeamDB){ //for testing purpose
        this.favoriteTeamDB=favoriteTeamDB
    }

    override fun onResume() {
        if(type== TYPE.FAVORITE) {
            var list = favoriteTeamDB.getAll()
            if(list.size!=view.data.size)
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

        Global.log("type : ${type}, req : ${req}");

        GlobalScope.launch(coroutineContext.main){

                Global.log("seharusnya launch");

                val data = Global.gson.fromJson(SportsDBApiAnko
                        .doRequest(req).await(),
                        TeamResponse::class.java
                )

                loadTeam(data)
        }
    }

    private fun loadTeam(response: TeamResponse) {

        view.data.clear();
        view.loading(false)

        Global.log("r size : " + response.teams.size)

        if(Global.nullOrEmpty(response.teams)){
            Global.log("load team : empty ")
            view.empty()
        }else{
            Global.log("load team : not empty ")
            view.data.addAll(response.teams)

            Global.log("data new size : ${view.data.size}")
        }

        //view.adapter.notifyDataSetChanged()
    }

    private fun loadFavorite() {

        val list = favoriteTeamDB.getAll()

        Global.log("Fav Team From DB (${list.size}), empty ? ${list.isEmpty()}")

        if(list.isEmpty()) {
            view.empty()
            return
        }

        view.data.clear()

        for ( f in list){
            view.data.add(Team.copy(f))
        }

        view.loading(false)

        view.adapter.notifyDataSetChanged()
    }

}