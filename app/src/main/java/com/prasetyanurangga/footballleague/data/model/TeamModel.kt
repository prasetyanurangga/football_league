package com.prasetyanurangga.footballleague.data.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    val items: List<TeamModel>
)

data class TeamModel(
    @SerializedName("idTeam")
    val IdTeam: String,
    @SerializedName("strTeam")
    val NameTeam: String,
    @SerializedName("strTeamBadge")
    val LogoUri: String
)