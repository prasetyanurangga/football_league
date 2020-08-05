package com.prasetyanurangga.footballleague.data.repository

import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.KlasemenModel
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

    suspend fun getEventDetails(id: String): List<EventModel> {
        return apiService.getEventDetails(id).items
    }


    suspend fun getSearchEvents(e: String): List<EventModel> {
        return apiService.getSearchEvents(e).items
    }

    suspend fun getSearchTeams(e: String): List<TeamModel> {
        return apiService.getSearchTeams(e).items
    }

    suspend fun getTeams(id: String): List<TeamModel> {
        return apiService.getTeams(id).items
    }

    suspend fun getDetailTeams(id: String): List<TeamModel> {
        return apiService.getDetailTeams(id).items
    }

    suspend fun getKlasemens(id: String): List<KlasemenModel> {
        return apiService.getKlasemens(id).items
    }
}