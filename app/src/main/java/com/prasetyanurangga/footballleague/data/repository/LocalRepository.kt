package com.prasetyanurangga.footballleague.data.repository

import com.prasetyanurangga.footballleague.data.dao.LocalDao
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.model.TeamModel


class LocalRepository(private val localDao: LocalDao) {

    fun getEvents(): List<EventModel> {
        return localDao.getEvents()
    }

    fun getEventByUid(uid : String): List<EventModel> {
        return localDao.getEventByUid(uid)
    }

    fun saveEvent(EventModel: EventModel){
        localDao.saveEvent(EventModel)
    }


    fun deleteEvent(EventModel: EventModel){
        localDao.deleteEvent(EventModel)
    }



    fun getTeams(): List<TeamModel> {
        return localDao.getTeams()
    }

    fun getTeamByUid(uid : String): List<TeamModel> {
        return localDao.getTeamByUid(uid)
    }

    fun saveTeam(teamModel: TeamModel){
        localDao.saveTeam(teamModel)
    }
    fun deleteTeam(teamModel: TeamModel){
        localDao.deleteTeam(teamModel)
    }

}