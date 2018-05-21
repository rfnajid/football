package com.nnn.footballclub.pages.main

import android.content.Context
import android.support.v4.app.Fragment
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.Global
import org.jetbrains.anko.toast


/**
 * Created by ridhaaaaazis on 21/05/18.
 */

open class ListPresenter (private val view : MainContract.ListView): MainContract.ListPresenter{

    enum class TYPE {
        PAST,NEXT,FAVORITE
    }

    internal val data : MutableList<Event> = mutableListOf()

    internal lateinit var adapter: RecyclerViewAdapter

    internal lateinit var favoriteDB: FavoriteDB
    private var _favSize = 0

    internal lateinit var context : Context


    override fun start() {
        context = checkNotNull((view as Fragment).context)
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

    override fun loadData() {
        view.empty(true)

        //child must run their own operation after called this function
    }

    override fun loadFavorite(list : List<Long>) {

        view.empty(list.isEmpty())

        _favSize=list.size

        if(list.isEmpty()) return

        data.clear()


        //child must run their own operation after called this function
    }

    override fun loadEvent(response: EventResponse, isFavorite: Boolean) {
        try{
            Global.log("load event (Parent) : ${view.type} : ${response.events.count()}")
        }catch (nullExc : NullPointerException){
            Global.log("response NULL BOSs")
            context.toast("There is no data")
            return
        }


        if(!isFavorite) data.clear()

        data.addAll(response.events)

        var isEmpty = data.size<=0

        view.empty(isEmpty)
        if(isEmpty) {
            adapter.notifyDataSetChanged()
            return
        }

        //child must run their own operation after called this function
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
        }
    }
}