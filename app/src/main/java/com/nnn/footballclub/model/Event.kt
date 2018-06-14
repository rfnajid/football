package com.nnn.footballclub.model

import com.google.gson.annotations.SerializedName
import com.nnn.footballclub.model.favorite.FavEvent
import com.nnn.footballclub.utils.Global
import java.io.Serializable
import java.util.*


/**
 * Created by ridhaaaaazis on 19/04/18.
 */
data class Event (

        //BASIC
        @SerializedName("idEvent") val id : Long,
        @SerializedName("strEvent") val name: String,
        @SerializedName("idHomeTeam") val homeId : Long,
        @SerializedName("idAwayTeam") val awayId : Long,

        var homeTeam : Team,
        var awayTeam : Team,
        @SerializedName("intHomeScore") val homeScore : Int?,
        @SerializedName("intAwayScore") val awayScore : Int?,
        @SerializedName("dateEvent") val date : Date,

        //DETAIL
        @SerializedName("strHomeGoalDetails")       private val homeGoals : String?,
        @SerializedName("strHomeRedCards")          private val homeRed : String?,
        @SerializedName("strHomeYellowCards")       private val homeYellow : String?,
        @SerializedName("strHomeLineupGoalkeeper")  private val homeGK : String? ,
        @SerializedName("strHomeLineupDefense")     private val homeDF : String? ,
        @SerializedName("strHomeLineupMidfield")    private val homeMF : String? ,
        @SerializedName("strHomeLineupForward")     private val homeFW : String? ,
        @SerializedName("strHomeLineupSubstitutes") private val homeSubs : String? ,
        @SerializedName("strHomeFormation")         private val homeFormation: String? ,
        @SerializedName("strAwayRedCards")          private val awayRed: String? ,
        @SerializedName("strAwayYellowCards")       private val awayYellow: String? ,
        @SerializedName("strAwayGoalDetails")       private val awayGoals : String? ,
        @SerializedName("strAwayLineupGoalkeeper")  private val awayGK : String? ,
        @SerializedName("strAwayLineupDefense")     private val awayDF : String? ,
        @SerializedName("strAwayLineupMidfield")    private val awayMF : String? ,
        @SerializedName("strAwayLineupForward")     private val awayFW : String? ,
        @SerializedName("strAwayLineupSubstitutes") private val awaySubs : String? ,
        @SerializedName("strAwayFormation")         private val awayFormation : String? ,
        @SerializedName("intHomeShots")             val homeShots : Int?,
        @SerializedName("intAwayShots")             val awayShots : Int?

) : Serializable{

    fun date () : String{
        return Global.dateToString(date)
    }

    //HOME
    fun homeGoals() : String?{
        if(homeGoals.isNullOrEmpty())
            return "-"
        return convert(homeGoals)
    }

    fun homeRed() : String?{
        if(homeRed.isNullOrEmpty())
            return "-"
        return convert(homeRed)
    }

    fun homeYellow() : String?{
        if(homeYellow.isNullOrEmpty())
            return "-"
        return convert(homeYellow)
    }

    fun homeGK() : String?{
        if(homeGK.isNullOrEmpty())
            return "-"
        return convert(homeGK)
    }

    fun homeDF() : String?{
        if(homeDF.isNullOrEmpty())
            return "-"
        return convert(homeDF)
    }

    fun homeMF() : String?{
        if(homeMF.isNullOrEmpty())
            return "-"
        return convert(homeMF)
    }

    fun homeFW() : String?{
        if(homeFW.isNullOrEmpty())
            return "-"
        return convert(homeFW)
    }

    fun homeFormation() : String?{
        if(homeFormation.isNullOrEmpty())
            return "-"
        return homeFormation
    }

    fun homeSubs() : String?{
        if(homeSubs.isNullOrEmpty())
            return "-"
        return convert(homeSubs)
    }
    
    //AWAY

    fun awayGoals() : String?{
        if(awayGoals.isNullOrEmpty())
            return "-"
        return convert(awayGoals)
    }

    fun awayRed() : String?{
        if(awayRed.isNullOrEmpty())
            return "-"
        return convert(awayRed)
    }

    fun awayYellow() : String?{
        if(awayYellow.isNullOrEmpty())
            return "-"
        return convert(awayYellow)
    }

    fun awayGK() : String?{
        if(awayGK.isNullOrEmpty())
            return "-"
        return convert(awayGK)
    }

    fun awayDF() : String?{
        if(awayDF.isNullOrEmpty())
            return "-"
        return convert(awayDF)
    }

    fun awayMF() : String?{
        if(awayMF.isNullOrEmpty())
            return "-"
        return convert(awayMF)
    }

    fun awayFW() : String?{
        if(awayFW.isNullOrEmpty())
            return "-"
        return convert(awayFW)
    }

    fun awayFormation() : String?{
        if(awayFormation.isNullOrEmpty())
            return "-"
        return awayFormation
    }

    fun awaySubs() : String?{
        if(awaySubs.isNullOrEmpty())
            return "-"
        return convert(awaySubs)
    }

    fun isACopyOfFavEvent() : Boolean{
        return (homeId==0L || awayId==0L)
    }

    private fun convert(value : String?) : String?{
        return value?.replace(";","\n")
    }

    companion object {
        fun copy(fav : FavEvent) : Event{
            return Event(
                    fav.id?.toLong() as Long,
                    fav.homeName+" vs "+fav.awayName,
                    0,0,
                    Team(0,fav.homeName.toString(),"",fav.homeLogo.toString(),
                            0,"","","","","",""),
                    Team(0,fav.awayName.toString(),"",fav.awayLogo.toString(),
                            0,"","","","","",""),
                    fav.homeScore?.toInt(), fav.awayScore?.toInt(),Global.stringToDate(fav.date.toString()),
                    "","","","","","",
                    "","","","","","","",
                    "","","","","",null,null
            )
        }
    }
}
