package com.nnn.footballclub.model

import com.google.gson.annotations.SerializedName
import com.nnn.footballclub.utils.S
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
        //@SerializedName("strHomeTeam") val homeTeam : String,
        //@SerializedName("strAwayTeam") val awayTeam : String,
        var homeTeam : Team,
        var awayTeam : Team,
        @SerializedName("intHomeScore") val homeScore : Int?,
        @SerializedName("intAwayScore") val awayScore : Int?,
        @SerializedName("dateEvent") val date : Date,

        //DETAIL
        @SerializedName("strHomeGoalDetails") val homeGoals : String? = "-",
        @SerializedName("strHomeRedCards") val homeRed : String? = "-",
        @SerializedName("strHomeYellowCards") val homeYellow : String? = "-",
        @SerializedName("strHomeLineupGoalkeeper") val homeGK : String? = "-",
        @SerializedName("strHomeLineupDefense") val homeDF : String? = "-",
        @SerializedName("strHomeLineupMidfield") val homeMF : String? = "-",
        @SerializedName("strHomeLineupForward") val homeFW : String? = "-",
        @SerializedName("strHomeLineupSubstitutes") val homeSubs : String? = "-",
        @SerializedName("strHomeFormation") val homeFormation: String? = "-",
        @SerializedName("strAwayRedCards") val awayRed: String? = "-",
        @SerializedName("strAwayYellowCards") val awayYellow: String? = "-",
        @SerializedName("strAwayGoalDetails") val awayGoals : String? = "-",
        @SerializedName("strAwayLineupGoalkeeper") val awayGK : String? = "-",
        @SerializedName("strAwayLineupDefense") val awayDF : String? = "-",
        @SerializedName("strAwayLineupMidfield") val awayMF : String? = "-",
        @SerializedName("strAwayLineupForward") val awayFW : String? = "-",
        @SerializedName("strAwayLineupSubstitutes") val awaySubs : String? = "-",
        @SerializedName("strAwayFormation") val awayFormation : String? = "-",
        @SerializedName("intHomeShots") val homeShots : Int? = 0,
        @SerializedName("intAwayShots") val awayShots : Int? = 0

) : Serializable{

    fun date () : String{
        return S.dateToString(date)
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

    fun awaySubs() : String?{
        if(awaySubs.isNullOrEmpty())
            return "-"
        return convert(awaySubs)
    }
    

    private fun convert(value : String?) : String?{
        return value?.replace(";","\n")
    }

}
