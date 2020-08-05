package com.prasetyanurangga.footballleague.data.model

import com.google.gson.annotations.SerializedName

data class KlasemenResponse(
    @SerializedName("table")
    val items: List<KlasemenModel>
)

data class KlasemenModel(
    @SerializedName("name")
    val Name: String,
    @SerializedName("teamid")
    val TeamId: String,
    @SerializedName("played")
    val Played: String,
    @SerializedName("goalsfor")
    val GoalFor: String,
    @SerializedName("goalsagainst")
    val GoalAgainst: String,
    @SerializedName("goalsdifference")
    val GoalDifference: String,
    @SerializedName("win")
    val Win: String,
    @SerializedName("draw")
    val Draw: String,
    @SerializedName("loss")
    val Loss: String,
    @SerializedName("total")
    val Total: String
)