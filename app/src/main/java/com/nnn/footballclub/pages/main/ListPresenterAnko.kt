package com.nnn.footballclub.pages.main


import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.Global.gson
import com.nnn.footballclub.utils.Global.log
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


/**
 *
 * Created by ridhaaaaazis on 18/05/18.
 *
 * Anko Coroutine Version
 *
 * */

class ListPresenterAnko (private val view : MainContract.ListView): ListPresenter(view) {

    override fun loadData() {
        super.loadData()

        lateinit var req: String
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

        async(UI) {
            val data = bg {
                gson.fromJson(SportsDBApiAnko
                        .doRequest(req),
                        EventResponse::class.java
                )
            }

            data.await()
            loadEvent(data.getCompleted())

        }
    }



    override fun loadFavorite(list : List<Long>) {
        //must call parent function first
        super.loadFavorite(list)

        for (i in 0..list.size-1){

            val id=list.get(i)

            async(UI){
                val data = bg {
                    gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.getEvent(id)),
                            EventResponse::class.java
                    )
                }

                data.await()

                loadEvent(data.getCompleted(),true)
                super.checkLoadFavorite(i,list.size)

                log("load favorite : ${i}")

            }
        }
    }

    override fun loadEvent(response: EventResponse, isFavorite: Boolean) {

        //must call parent function first
        super.loadEvent(response, isFavorite)

        for(i in 0..data.size-1){
            val d = data.get(i)
            log("EVENT : ${d.name}")

            async(UI){
                val data = bg {
                    gson.fromJson(SportsDBApiAnko
                            .doRequest(SportsDBApiAnko.teamDetail(d.homeId)),
                            TeamResponse::class.java
                    )
                }

                data.await()

                loadTeam(data.getCompleted(),i,true)


                async(UI) {
                    val data = bg {
                        gson.fromJson(SportsDBApiAnko
                                .doRequest(SportsDBApiAnko.teamDetail(d.awayId)),
                                TeamResponse::class.java
                        )
                    }


                    data.await()

                    loadTeam(data.getCompleted(), i, false)
                    super.checkLoadEvent(i)
                }

            }
        }
    }
}