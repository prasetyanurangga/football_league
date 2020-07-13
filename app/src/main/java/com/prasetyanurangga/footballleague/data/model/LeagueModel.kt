package com.prasetyanurangga.footballleague.data.model

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("leagues")
    val items: List<LeagueModel>
)

data class LeagueModel(
    @SerializedName("idLeague")
    val IdLeague: String,
    @SerializedName("strLeague")
    val NameLeague: String,
    @SerializedName("strLeagueAlternate")
    val AltNameLeague: String,
    @SerializedName("strCurrentSeason")
    val CurrentSeason: String,
    @SerializedName("intFormedYear")
    val FormedYear: String,
    @SerializedName("dateFirstEvent")
    val FistEvent: String,
    @SerializedName("strCountry")
    val Country: String,
    @SerializedName("strBadge")
    val LogoUri: String,
    @SerializedName("strDescriptionEN")
    val Description: String
)