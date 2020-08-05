package com.prasetyanurangga.footballleague.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    val items: List<TeamModel>
)
@Entity(
    tableName = "Team"
)
data class TeamModel(
    @PrimaryKey()
    @ColumnInfo(name ="idTeam")
    @SerializedName("idTeam")
    val IdTeam: String,

    @ColumnInfo(name ="strTeam")
    @SerializedName("strTeam")
    val NameTeam: String,

    @ColumnInfo(name ="strTeamBadge")
    @SerializedName("strTeamBadge")
    val LogoUri: String,

    @ColumnInfo(name ="strDescriptionEN")
    @SerializedName("strDescriptionEN")
    var DescTeam: String,

    @ColumnInfo(name ="strLeague")
    @SerializedName("strLeague")
    val LeagueTeam: String,

    @ColumnInfo(name ="strStadium")
    @SerializedName("strStadium")
    val StadiumTeam: String,

    @ColumnInfo(name ="intFormedYear")
    @SerializedName("intFormedYear")
    val FormedYearTeam: String,

    @ColumnInfo(name ="strSport")
    @SerializedName("strSport")
    val SportTeam: String

)