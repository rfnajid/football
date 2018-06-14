package com.nnn.footballclub.model.favorite


/**
 * Created by ridhaaaaazis on 03/06/18.
 */

data class FavEvent(
        val id : Int?,
        val date : String?,
        val homeName : String?,
        val homeLogo : String?,
        val homeScore : Int?,
        val awayName : String?,
        val awayLogo : String?,
        val awayScore : Int?
){
    companion object {
        const val TABLE: String = "EVENT_FAVORITE"
        const val ID: String = "ID"
        const val DATE : String = "DATE"
        const val HOMENAME : String = "HOMENAME"
        const val HOMESCORE : String = "HOMESCORE"
        const val HOMELOGO : String = "HOMELOGO"
        const val AWAYNAME : String = "AWAYNAME"
        const val AWAYSCORE : String = "AWAYSCORE"
        const val AWAYLOGO : String = "AWAYLOGO"
    }
}