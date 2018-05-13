package com.nnn.footballclub.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by ridhaaaaazis on 12/05/18.
 */
data class Team(
        @SerializedName("idTeam") val id : Long,
        @SerializedName("strTeam") val name : String,
        @SerializedName("strTeamBadge") val logo : String
) : Serializable