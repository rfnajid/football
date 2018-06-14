package com.nnn.footballclub.model

import com.google.gson.annotations.SerializedName
import com.nnn.footballclub.utils.Global
import java.io.Serializable
import java.util.*


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

data class Player(
        @SerializedName("idPlayer") val id : Long,
        @SerializedName("strPlayer") val name : String,
        @SerializedName("dateBorn") val born : Date,
        @SerializedName("strNationality") val nationality : String,
        @SerializedName("strPosition") val position : String,
        @SerializedName("strTeam") val team : String,
        @SerializedName("strHeight") val height : String,
        @SerializedName("strWeight") val weight : String,
        @SerializedName("strCutout") val pp : String,
        @SerializedName("strDescriptionEN") val desc : String
) : Serializable {
    fun born() : String{
        return Global.dateToString(born,Global.dateOnlyFormat())
    }
}
