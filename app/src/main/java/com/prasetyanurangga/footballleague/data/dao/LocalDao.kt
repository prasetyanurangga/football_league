package com.prasetyanurangga.footballleague.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.TeamModel

@Dao
interface LocalDao {

    @Query( "SELECT * FROM Event" )
    fun getEvents(): List<EventModel>

    @Query( "SELECT * FROM Event WHERE IdEvent = :uid" )
    fun getEventByUid(uid: String): List<EventModel>

    @Insert
    fun saveEvent(vararg user: EventModel)
    
    @Delete
    fun deleteEvent(vararg user: EventModel)



    @Query( "SELECT * FROM Team")
    fun getTeams(): List<TeamModel>

    @Query( "SELECT * FROM Team WHERE IdTeam = :uid" )
    fun getTeamByUid(uid: String): List<TeamModel>

    @Insert
    fun saveTeam(vararg user: TeamModel)

    @Delete
    fun deleteTeam(vararg user: TeamModel)



}