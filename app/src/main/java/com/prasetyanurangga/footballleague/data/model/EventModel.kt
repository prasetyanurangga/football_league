package com.prasetyanurangga.footballleague.data.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("events")
    val items: List<EventModel>
)

data class EventModel(
    @SerializedName("idEvent")
    val IdEvent: String,
    @SerializedName("strEvent")
    val NameEvent: String,
    @SerializedName("strLeague")
    val NameLeague: String,
    @SerializedName("strHomeTeam")
    val HomeTeam: String,
    @SerializedName("strAwayTeam")
    val AwayTeam: String,
    @SerializedName("intHomeScore")
    val HomeScore: String,
    @SerializedName("intAwayScore")
    val AwayScore: String,
    @SerializedName("strTime")
    val TimeEvent: String,
    @SerializedName("dateEvent")
    val DateEvent: String
)