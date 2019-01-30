package com.nnn.footballclub.utils.network

import com.nnn.footballclub.BuildConfig
import com.nnn.footballclub.utils.Global
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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

    fun getTeams(idLeague : Long) : String{
        return "${BuildConfig.END_POINT}lookup_all_teams.php?id=${idLeague}"
    }

    fun getTeam(id : Long): String{
        return "${BuildConfig.END_POINT}lookupteam.php?id=${id}"
    }

    fun getPlayers(idTeam : Long) : String{
        val url = "${BuildConfig.END_POINT}lookup_all_players.php?id=${idTeam}"
        Global.log(url)
        return url
    }

    fun searchTeam(query : String) : String {
        val url = "${BuildConfig.END_POINT}searchteams.php?t=${query}"
        Global.log(url)
        return url
    }

    fun searchMatch(query : String) : String{
        val url = "${BuildConfig.END_POINT}searchevents.php?e=${query}"
        Global.log(url)
        return url
    }

    fun doRequest(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }

    fun doRequestString(url: String): String {
        return URL(url).readText()
    }
}