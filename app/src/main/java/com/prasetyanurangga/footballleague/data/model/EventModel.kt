package com.prasetyanurangga.footballleague.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EventResponses(
    @SerializedName("events")
    val items: List<EventModel>
)
data class EventResponse(
    @SerializedName("event")
    val items: List<EventModel>
)

@Entity(
    tableName = "Event"
)
data class EventModel(
    @PrimaryKey()
    @ColumnInfo(name ="idEvent")
    @SerializedName("idEvent")
    val IdEvent: String,

    @ColumnInfo(name ="strEvent")
    @SerializedName("strEvent")
    val NameEvent: String,

    @ColumnInfo(name ="strLeague")
    @SerializedName("strLeague")
    val NameLeague: String,

    @ColumnInfo(name ="strHomeTeam")
    @SerializedName("strHomeTeam")
    val HomeTeam: String,

    @ColumnInfo(name ="strAwayTeam")
    @SerializedName("strAwayTeam")
    val AwayTeam: String,

    @ColumnInfo(name ="intHomeScore")
    @SerializedName("intHomeScore")
    var HomeScore: String,

    @ColumnInfo(name ="intAwayScore")
    @SerializedName("intAwayScore")
    var AwayScore: String,

    @ColumnInfo(name ="strTime")
    @SerializedName("strTime")
    val TimeEvent: String,

    @ColumnInfo(name ="dateEvent")
    @SerializedName("dateEvent")
    val DateEvent: String,

    @ColumnInfo(name ="strHomeFormation")
    @SerializedName("strHomeFormation")
    var HomeFormation: String,

    @ColumnInfo(name ="strAwayFormation")
    @SerializedName("strAwayFormation")
    var AwayFormation: String,

    @ColumnInfo(name ="idHomeTeam")
    @SerializedName("idHomeTeam")
    val IdHome: String,

    @ColumnInfo(name ="idAwayTeam")
    @SerializedName("idAwayTeam")
    val IdAway: String,

    @ColumnInfo(name ="intHomeShots")
    @SerializedName("intHomeShots")
    var HomeShot: String,

    @ColumnInfo(name ="intAwayShots")
    @SerializedName("intAwayShots")
    var AwayShot: String,

    @ColumnInfo(name ="strHomeGoalDetails")
    @SerializedName("strHomeGoalDetails")
    var HomeGoal: String,

    @ColumnInfo(name ="strAwayGoalDetails")
    @SerializedName("strAwayGoalDetails")
    var AwayGoal: String,

    @ColumnInfo(name ="strHomeRedCards")
    @SerializedName("strHomeRedCards")
    var HomeRed: String,

    @ColumnInfo(name ="strAwayRedCards")
    @SerializedName("strAwayRedCards")
    var AwayRed: String,

    @ColumnInfo(name ="strHomeYellowCards")
    @SerializedName("strHomeYellowCards")
    var HomeYellow: String,

    @ColumnInfo(name ="strAwayYellowCards")
    @SerializedName("strAwayYellowCards")
    var AwayYellow: String,

    @ColumnInfo(name ="idLeague")
    @SerializedName("idLeague")
    val IdLeague: String,

    @SerializedName("strSport")
    val SportName: String,

    @ColumnInfo(name ="ImgHome")
    var ImgHome: String?,
    @ColumnInfo(name ="ImgAway")
    var ImgAway: String?
)