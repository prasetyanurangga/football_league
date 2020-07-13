package com.prasetyanurangga.footballleague.data.repository

import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.service.ApiService

class ApiRepository(private val apiService: ApiService) {


    suspend fun getLeagues(id: String): List<LeagueModel> {
        return apiService.getLeagues(id).items
    }

    suspend fun getEvents(id: String): List<EventModel> {
        return apiService.getEvents(id).items
    }

    suspend fun getEventNexts(id: String): List<EventModel> {
        return apiService.getEventNexts(id).items
    }

    suspend fun getTeams(id: String): List<TeamModel> {
        return apiService.getTeams(id).items
    }
}