package com.nnn.footballclub.model.db

import android.content.Context
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.favorite.FavTeam
import com.nnn.footballclub.utils.helper.database
import org.jetbrains.anko.db.*


/**
 * Created by ridhaaaaazis on 14/05/18.
 */

class FavoriteTeamDB(val context : Context?){

    fun add(team : Team){
        context?.database?.use {
            insert(FavTeam.TABLE,
                    FavTeam.ID to team.id,
                    FavTeam.NAME to team.name,
                    FavTeam.LOGO to team.logo
                    )
        }
    }

    fun remove(team : Team){
        context?.database?.use {
            delete(FavTeam.TABLE, "(${FavTeam.ID} = {id})",
                    "id" to team.id)
        }
    }

    fun isExist(team : Team) : Boolean{
        var found=false
        context?.database?.use {
            val e = select(FavTeam.TABLE)
                    .whereArgs("(${FavTeam.ID} = {id})","id" to team.id)
                    .parseOpt(classParser<FavTeam>())

            if (e != null) {
                found=true
            }
        }
        return found
    }

    fun getAll() : List<FavTeam>{
        lateinit var data : List<FavTeam>
        context?.database?.use{
            data=select(FavTeam.TABLE)
                    .orderBy(FavTeam.NAME,SqlOrderDirection.DESC)
                    .parseList(classParser<FavTeam>())
        }
        return data
    }
}