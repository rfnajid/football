package com.nnn.footballclub.pages.detail.team

import android.content.Context
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.db.FavoriteTeamDB
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.base.BaseDetailContract
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


/**
 * Created by ridhaaaaazis on 04/06/18.
 */

class TeamDetailPresenter (
        private var team : Team,
        private val view : BaseDetailContract._View<Team>,
        val coroutineContext : CoroutineContextProvider = CoroutineContextProvider()
) : BaseDetailContract._Presenter {

    private lateinit var context : Context
    private lateinit var favoriteDB : FavoriteTeamDB
    private var isFavorite : Boolean= false

    override fun start(context : Context) {
        this.context=context
        start(context,FavoriteTeamDB(context))
    }

    internal fun start(context: Context, favoriteTeamDB: FavoriteTeamDB){
        this.favoriteDB=favoriteTeamDB
        isFavorite = favoriteDB.isExist(team)
        view.updateFavoriteLayout(isFavorite)

        Global.log("isCopy : ${team.isACopyOfFavTeam()}")

        if(team.isACopyOfFavTeam()){

            async(coroutineContext.main) {
                val data = bg {
                    Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getTeam(team.id)),
                            TeamResponse::class.java
                    )
                }
                team = data.await().teams.get(0)
                Global.log(team.toString())

                view.loadData(team)
            }

        }else{
            view.loadData(team)
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
            favoriteDB.remove(team)
        }else{
            favoriteDB.add(team)
        }
        isFavorite=!isFavorite
        view.updateFavoriteLayout(isFavorite)
    }
}