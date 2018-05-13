package com.nnn.footballclub.utils.network

import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.utils.S
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by ridhaaaaazis on 01/05/18.
 */

interface EventApi {

    @GET("eventspastleague.php")
    fun getLast (
            @Query("id") id : Long
    ) : Observable<EventResponse>

    @GET("eventsnextleague.php")
    fun getNext(
            @Query("id") id : Long
    ) : Observable<EventResponse>

    @GET("lookupteam.php")
    fun teamDetail(
            @Query("id") id : Long
    ) : Observable<TeamResponse>

    companion object {
        fun create():EventApi{
            val retrofit = S.retrofit()
            return retrofit.create(EventApi::class.java)
        }
    }


}