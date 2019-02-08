package com.nnn.footballclub.pages.detail.match

import android.content.Context
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteEventDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.base.BaseDetailContract
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.*

/**
 * Created by ridhaaaaazis on 18/05/18.
 */

class MatchDetailPresenter (private var event : Event,
                            private val view : BaseDetailContract._View<Event>,
                            var coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) : BaseDetailContract._Presenter {

    private lateinit var context : Context
    private lateinit var favoriteEventDB : FavoriteEventDB
    private var isFavorite : Boolean= false

    override fun start(context : Context) {
        this.context=context
        start(context,FavoriteEventDB(context))
    }

    fun start(context: Context, favoriteEventDB: FavoriteEventDB){ //for testing
        this.favoriteEventDB=favoriteEventDB
        isFavorite = favoriteEventDB.isExist(event)
        view.updateFavoriteLayout(isFavorite)

        Global.log("hid : ${event.homeId}, aid : ${event.awayId}, isCopy : ${event.isACopyOfFavEvent()}")

        if(event.isACopyOfFavEvent()){

            GlobalScope.async(Dispatchers.Main) {
                val data = withContext(Dispatchers.IO) {
                    Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getEvent(event.id)).await(),
                            EventResponse::class.java
                    )
                }
                event = data.events.get(0)
                Global.log(event.toString())

                loadTeam()
            }

        }else{
            view.loadData(event)
        }
    }

    override fun onOptionsItemSelected(id: Int?) : Boolean {
        return when (id){
            R.id.menu_favorite -> {
                favorite()
                true
            }
            else -> false
        }
    }

    override fun favorite() {
        if(isFavorite){
            favoriteEventDB.remove(event)
        }else{
            favoriteEventDB.add(event)
        }
        isFavorite=!isFavorite
        view.updateFavoriteLayout(isFavorite)
    }


    private fun loadTeam(){
        GlobalScope.launch(Dispatchers.Main){
            val data = Global.gson.fromJson(SportsDBApiAnko
                    .doRequest(SportsDBApiAnko.getTeam(event.homeId)).await(),
                    TeamResponse::class.java)

            event.homeTeam=data.teams.get(0)

            launch(Dispatchers.Main) {
                    val data =Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getTeam(event.awayId)).await(),
                            TeamResponse::class.java)

                event.awayTeam=data.teams.get(0)

                view.loadData(event)
            }

        }
    }
}