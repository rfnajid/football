package com.nnn.footballclub.model

import com.google.gson.annotations.SerializedName
import com.nnn.footballclub.model.favorite.FavTeam
import java.io.Serializable


/**
 * Created by ridhaaaaazis on 12/05/18.
 */
data class Team(
        @SerializedName("idTeam")           val id : Long,
        @SerializedName("strTeam")          val name : String,
        @SerializedName("strAlternate")     val alias : String,
        @SerializedName("strTeamBadge")     val logo : String,
        @SerializedName("intFormedYear")    val formedYear : Int,
        @SerializedName("strManager")       val manager : String,
        @SerializedName("strLeague")        val league : String,
        @SerializedName("strStadium")       val stadium : String,
        @SerializedName("strStadiumLocation")val city : String,
        @SerializedName("strStadiumThumb")  val stadiumImage : String,
        @SerializedName("strDescriptionEN") val description : String
) : Serializable {

    fun city() : String {
        return city.substringAfterLast(",")
    }

    fun isACopyOfFavTeam() : Boolean{
        return (description.isNullOrEmpty())
    }

    companion object {
        fun copy(fav : FavTeam) : Team{
            return Team(
                    fav.id?.toLong() as Long,
                    fav.name,"",fav.logo,
                    0,"","","",
                    "","",""
            )
        }
    }
}