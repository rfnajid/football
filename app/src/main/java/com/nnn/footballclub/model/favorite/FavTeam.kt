package com.nnn.footballclub.model.favorite


/**
 * Created by ridhaaaaazis on 03/06/18.
 */

data class FavTeam(
        val id : Int?,
        val name : String,
        val logo : String
){

    companion object {
        const val TABLE: String = "TEAM_FAVORITE"
        const val ID: String = "ID"
        const val NAME: String = "NAME"
        const val LOGO : String = "LOGO"
    }
}