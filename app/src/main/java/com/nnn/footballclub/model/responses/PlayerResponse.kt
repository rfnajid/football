package com.nnn.footballclub.model.responses

import com.google.gson.annotations.SerializedName
import com.nnn.footballclub.model.Player


/**
 * Created by ridhaaaaazis on 12/05/18.
 */
data class PlayerResponse (
    @SerializedName("player") val players : List<Player>
)