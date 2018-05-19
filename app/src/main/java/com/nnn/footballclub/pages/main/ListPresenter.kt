package com.nnn.footballclub.pages.main


import android.content.Context
import android.support.v4.app.Fragment
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.db.FavoriteDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.EventApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast


/**
 * Created by ridhaaaaazis on 18/05/18.
 */

class ListPresenter (view : MainContract.ListView): MainContract.ListPresenter{

    enum class TYPE {
        PAST,NEXT,FAVORITE
    }

    private val view = view
    private val eventApiServe by lazy {
        EventApi.create()
    }

    private val data : MutableList<Event> = mutableListOf()

    lateinit var adapter: RecyclerViewAdapter

    private lateinit var favoriteDB: FavoriteDB
    private var _favSize = 0
    private lateinit var disposable: Disposable
    private lateinit var context : Context


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

        lateinit var call: Observable<EventResponse>
        when (view.type) {
            TYPE.NEXT -> {
                call = eventApiServe.getNext(Global.idLeague)
            }
            TYPE.PAST -> {
                call = eventApiServe.getPast(Global.idLeague)
            }
            TYPE.FAVORITE -> {
                return
            }
        }


        disposable = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response -> loadEvent(response) },
                        { error -> Global.error(context,"${error.message}") }
                )

    }

    override fun loadFavorite(list : List<Long>) {
        view.empty(list.isEmpty())

        _favSize=list.size

        if(list.isEmpty()) return

        data.clear()

        Global.log("FAVOURITE IN DB : "+list.count())

        var i = 0
        for (id in list){
            disposable = eventApiServe.getEvent(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {response -> loadEvent(response,true)},
                            {error -> Global.error(context,"Can't load fav id : ${id}")},
                            {
                                i++
                                Global.log("get event detail : ${i} completed")
                                if(i>list.count()){
                                    Global.log("GET ALL FAV DETAIL COMPLETED")
                                }
                            }
                    )

        }
    }

    override fun loadEvent(response: EventResponse, isFavorite: Boolean) {
        try{
            Global.log("load response : ${view.type} : ${response.events.count()}")
        }catch (nullExc : NullPointerException){
            Global.log("response NULL BOS")
            context.toast("There is no data")
            return
        }

        if(!isFavorite) data.clear()
        data.addAll(response.events)

        view.empty(response.events.isEmpty())
        if(response.events.isEmpty()) {
            adapter.notifyDataSetChanged()
            return
        }

        var i = 1
        for(d in data){
            //getting team detail
            disposable = eventApiServe.teamDetail(d.homeId)
                    .concatMap {
                        response -> loadTeam(response,d,true)
                        return@concatMap eventApiServe.teamDetail(d.awayId) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                response -> loadTeam(response,d,false)
                            },
                            { err -> Global.error(context,"Can't load Team Detail") },
                            {
                                Global.log("get team detail event : ${i} COMPLETED")
                                i++
                                if(i>data.count()){
                                    Global.log("GET TEAM DETAIL ALL COMPLETED")
                                    adapter.notifyDataSetChanged()
                                }
                            }
                    )
        }
    }

    override fun loadTeam(response: TeamResponse, event: Event, isHome: Boolean) {
        val team : Team = response.teams[0]
        if(isHome){
            event.homeTeam=team
        }else{
            event.awayTeam=team
        }
        Global.log("Loaded : ${team.name} logo : ${team.logo}")
    }
}