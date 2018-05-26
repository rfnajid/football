package com.nnn.footballclub.pages.main

import android.content.Context
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


/**
 * Created by ridhaaaaazis on 21/05/18.
 */

open class ListPresenter (private val view : MainContract.ListView,private val coroutineContext : CoroutineContextProvider = CoroutineContextProvider()): MainContract.ListPresenter{

    enum class TYPE {
        PAST,NEXT,FAVORITE
    }

    internal val data : MutableList<Event> = mutableListOf()

    internal lateinit var adapter: RecyclerViewAdapter

    internal lateinit var favoriteDB: FavoriteDB
    private var _favSize = 0

    internal lateinit var context : Context


    override fun start(context : Context) {
        adapter = RecyclerViewAdapter(context,data)
        favoriteDB = FavoriteDB(context)
    }

    override fun onResume() {
        if(view.type==TYPE.FAVORITE) {
            var list = favoriteDB.getAll()
            if(list.size!=_favSize)
                loadFavorite(list)
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

    fun checkLoadFavorite(i : Int,count : Int){
        Global.log("checkLoadFavorite -> i : ${i}, count : ${count}")
        if(i>=count-1){
            Global.log("GET ALL FAV DETAIL COMPLETED")
            Global.log("FINAL FAVORITE DATA : ${data.size}")
        }
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

        view.loading()

        lateinit var req: String

        Global.log("TTYPE : ${view.type}")

        when (view.type) {
            TYPE.NEXT -> {
                req = SportsDBApiAnko.getNext(Global.idLeague)
            }
            TYPE.PAST -> {
                req = SportsDBApiAnko.getPast(Global.idLeague)
            }
            TYPE.FAVORITE -> {
                return
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



    override fun loadFavorite(list : List<Long>) {
        view.loading()

        Global.log("Fav From DB (${list.size}, : $list")

        _favSize=list.size

        if(list.isEmpty()) {
            view.empty()
            return
        }

        data.clear()


        for (i in 0..(list.size-1)){

            val id=list.get(i)

            Global.log("Favorite i : $i, id=$id")

            async(coroutineContext.main){
                val data = bg {
                    Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getEvent(id)),
                            EventResponse::class.java
                    )
                }

                data.await()

                loadEvent(data.getCompleted(),true)
                checkLoadFavorite(i,list.size)

                Global.log("load favorite : ${i}")

            }
        }
    }

    override fun loadEvent(response: EventResponse, isFavorite: Boolean) {

        try{
            Global.log("load event (Parent) : ${view.type} : ${response.events.count()}")
        }catch (nullExc : NullPointerException){
            if(!isFavorite) {
                Global.log("response NULL BOSs")
                view.empty(true)
                return
            }
        }

        if(!isFavorite) data.clear()

        data.addAll(response.events)

        if(data.size<=0) {
            view.empty()
            adapter.notifyDataSetChanged()
            return
        }

        for(i in 0..(data.size-1)){
            val d = data.get(i)
            Global.log("EVENT : ${d.name}")

            async(coroutineContext.main){
                val data = bg {
                    Global.gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.teamDetail(d.homeId)),
                            TeamResponse::class.java
                    )
                }

                data.await()

                loadTeam(data.getCompleted(),i,true)


                async(coroutineContext) {
                    val data = bg {
                        Global.gson.fromJson(SportsDBApiAnko
                                .doRequest(SportsDBApiAnko.teamDetail(d.awayId)),
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