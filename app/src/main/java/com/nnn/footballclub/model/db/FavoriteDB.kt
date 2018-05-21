package com.nnn.footballclub.model.db

import android.content.Context
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.utils.helper.database
import org.jetbrains.anko.db.*


/**
 * Created by ridhaaaaazis on 14/05/18.
 */

class FavoriteDB(val context : Context?){

    fun add(event : Event){
        context?.database?.use {
            insert(Event.TABLE_FAVORITE,
                    Event.ID to event.id)
        }
    }

    fun remove(event : Event){
        context?.database?.use {
            delete(Event.TABLE_FAVORITE, "(${Event.ID} = {id})",
                    "id" to event.id)
        }
    }

    fun isExist(event : Event) : Boolean{
        var found=false
        context?.database?.use {
            val e = select(Event.TABLE_FAVORITE)
                    .whereArgs("(${Event.ID} = {id})","id" to event.id)
                    .parseOpt(LongParser)

            if (e != null) {
                found=true
            }
        }
        return found
    }

    fun getAll() : List<Long>{
        lateinit var data : List<Long>
        context?.database?.use{
            data=select(Event.TABLE_FAVORITE)
                    .orderBy(Event.ID,SqlOrderDirection.DESC)
                    .parseList(LongParser)
        }
        return data
    }
}