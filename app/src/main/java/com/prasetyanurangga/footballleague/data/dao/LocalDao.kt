package com.prasetyanurangga.footballleague.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prasetyanurangga.footballleague.data.model.EventModel

@Dao
interface LocalDao {

    @Query( "SELECT * FROM Event WHERE IdLeague = :idLeague" )
    fun getEvents(idLeague: String): List<EventModel>

    @Query( "SELECT * FROM Event WHERE IdEvent = :uid" )
    fun getEventByUid(uid: String): List<EventModel>

    @Insert
    fun saveEvent(vararg user: EventModel)

    @Update
    fun updateEvent(vararg user: EventModel)

    @Delete
    fun deleteEvent(vararg user: EventModel)



}