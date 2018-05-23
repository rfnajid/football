package com.nnn.footballclub.utils.network

import com.nnn.footballclub.BuildConfig
import java.net.URL

/**
 * Created by ridhaaaaazis on 20/05/18.
 */

/**
 * Anko Coroutine Version
 * */

object SportsDBApiAnko{

    fun getPast(id : Long?): String {
        return "${BuildConfig.END_POINT}eventspastleague.php?id=${id}"
    }

    fun getNext(id : Long?): String {
        return "${BuildConfig.END_POINT}eventsnextleague.php?id=${id}"
    }

    fun getEvent(id : Long?): String {
        return "${BuildConfig.END_POINT}lookupevent.php?id=${id}"
    }

    fun teamDetail(id : Long): String{
        return "${BuildConfig.END_POINT}lookupteam.php?id=${id}"
    }

    fun doRequest(url: String): String {
        return URL(url).readText()
    }

}