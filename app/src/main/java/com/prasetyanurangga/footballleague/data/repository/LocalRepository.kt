package com.prasetyanurangga.footballleague.data.repository

import com.prasetyanurangga.footballleague.data.dao.LocalDao
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.LeagueModel


class LocalRepository(private val localDao: LocalDao) {

    fun getEvents(idLeague : String): List<EventModel> {
        return localDao.getEvents(idLeague)
    }

    fun getEventByUid(uid : String): List<EventModel> {
        return localDao.getEventByUid(uid)
    }

    fun saveEvent(EventModel: EventModel){
        localDao.saveEvent(EventModel)
    }

    fun updateEvent(EventModel: EventModel){
        localDao.updateEvent(EventModel)
    }

    fun deleteEvent(EventModel: EventModel){
        localDao.deleteEvent(EventModel)
    }

}