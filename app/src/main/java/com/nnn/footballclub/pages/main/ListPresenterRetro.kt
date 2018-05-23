package com.nnn.footballclub.pages.main


import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.network.SportsDBApiRetro
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 *
 * Created by ridhaaaaazis on 18/05/18.
 *
 * Retrofit + RxJava Version
 *
 * */

class ListPresenterRetro (private val view : MainContract.ListView): ListPresenter(view) {


    private val eventApiServe by lazy {
        SportsDBApiRetro.create()
    }
    private lateinit var disposable: Disposable

    override fun loadData() {
        super.loadData()

        lateinit var req: Observable<EventResponse>
        when (view.type) {
            TYPE.NEXT -> {
                req = eventApiServe.getNext(Global.idLeague)
            }
            TYPE.PAST -> {
                req = eventApiServe.getPast(Global.idLeague)
            }
            TYPE.FAVORITE -> {
                return
            }
        }


        disposable = req
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response -> loadEvent(response) },
                        { error -> Global.error(context,"${error.message}") }
                )

    }

    override fun loadFavorite(list : List<Long>) {
        //must call parent function first
        super.loadFavorite(list)

        for (i in 0..list.size-1){
            val id = list.get(i)
            disposable = eventApiServe.getEvent(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {response -> loadEvent(response,true)},
                            {error -> Global.error(context,"Can't load fav id : ${id}")},
                            {
                                super.checkLoadFavorite(i,list.size)
                            }
                    )

        }
    }

    override fun loadEvent(response: EventResponse, isFavorite: Boolean) {
        //must call parent function first
        super.loadEvent(response, isFavorite)

        for(i in 0..data.size-1){
            //getting team detail
            val d = data.get(i)
            disposable = eventApiServe.teamDetail(d.homeId)
                    .concatMap {
                        response -> loadTeam(response,i,true)
                        return@concatMap eventApiServe.teamDetail(d.awayId) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                response -> loadTeam(response,i,false)
                            },
                            { err -> Global.error(context,"Can't load Team Detail") },
                            {
                                super.checkLoadEvent(i)
                            }
                    )
        }
    }
}