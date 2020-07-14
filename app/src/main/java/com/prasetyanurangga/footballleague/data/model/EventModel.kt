package com.prasetyanurangga.footballleague.data.model

import com.google.gson.annotations.SerializedName

data class EventResponses(
    @SerializedName("events")
    val items: List<EventModel>
)
data class EventResponse(
    @SerializedName("event")
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
    val DateEvent: String,
    @SerializedName("strHomeFormation")
    val HomeFormation: String,
    @SerializedName("strAwayFormation")
    val AwayFormation: String,
    @SerializedName("idHomeTeam")
    val IdHome: String,
    @SerializedName("idAwayTeam")
    val IdAway: String,
    @SerializedName("intHomeShots")
    val HomeShot: String,
    @SerializedName("intAwayShots")
    val AwayShot: String,
    @SerializedName("strHomeGoalDetails")
    val HomeGoal: String,
    @SerializedName("strAwayGoalDetails")
    val AwayGoal: String,
    @SerializedName("strHomeRedCards")
    val HomeRed: String,
    @SerializedName("strAwayRedCards")
    val AwayRed: String,
    @SerializedName("strHomeYellowCards")
    val HomeYellow: String,
    @SerializedName("strAwayYellowCards")
    val AwayYellow: String
)