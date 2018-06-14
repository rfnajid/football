package com.nnn.footballclub.model.db

import android.content.Context
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.favorite.FavEvent
import com.nnn.footballclub.utils.helper.database
import org.jetbrains.anko.db.*


/**
 * Created by ridhaaaaazis on 14/05/18.
 */

class FavoriteEventDB(val context : Context?){

    fun add(event : Event){
        context?.database?.use {
            insert(FavEvent.TABLE,
                    FavEvent.ID to event.id,
                    FavEvent.DATE to event.date(),
                    FavEvent.HOMENAME to event.homeTeam.name,
                    FavEvent.HOMELOGO to event.homeTeam.logo,
                    FavEvent.HOMESCORE to event.homeScore,
                    FavEvent.AWAYNAME to event.awayTeam.name,
                    FavEvent.AWAYLOGO to event.awayTeam.logo,
                    FavEvent.AWAYSCORE to event.awayScore
                    )
        }
    }

    fun remove(event : Event){
        context?.database?.use {
            delete(FavEvent.TABLE, "(${FavEvent.ID} = {id})",
                    "id" to event.id)
        }
    }

    fun isExist(event : Event) : Boolean{
        var found=false
        context?.database?.use {
            val e = select(FavEvent.TABLE)
                    .whereArgs("(${FavEvent.ID} = {id})","id" to event.id)
                    .parseOpt(classParser<FavEvent>())

            if (e != null) {
                found=true
            }
        }
        return found
    }

    fun getAll() : List<FavEvent>{
        lateinit var data : List<FavEvent>
        context?.database?.use{
            data=select(FavEvent.TABLE)
                    .orderBy(FavEvent.ID,SqlOrderDirection.DESC)
                    .parseList(classParser<FavEvent>())
        }
        return data
    }
}