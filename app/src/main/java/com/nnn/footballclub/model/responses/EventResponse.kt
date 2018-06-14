package com.nnn.footballclub.model.responses

import com.google.gson.annotations.SerializedName
import com.nnn.footballclub.model.Event


/**
 * Created by ridhaaaaazis on 11/05/18.
 */

data class EventResponse(
        @SerializedName (value="events", alternate=arrayOf("event")) val events : List<Event>
)