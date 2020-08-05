package com.prasetyanurangga.footballleague.data.service

import com.prasetyanurangga.footballleague.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("lookupleague.php")
    suspend fun getLeagues(@Query("id") id: String): LeagueResponse

    @GET("eventspastleague.php")
    suspend fun getEvents(@Query("id") id: String): EventResponses

    @GET("eventsnextleague.php")
    suspend fun getEventNexts(@Query("id") id: String): EventResponses

    @GET("searchevents.php")
    suspend fun getSearchEvents(@Query("e") e: String): EventResponse

    @GET("searchteams.php")
    suspend fun getSearchTeams(@Query("t") e: String): TeamResponse

    @GET("lookupevent.php")
    suspend fun getEventDetails(@Query("id") id: String): EventResponses

    @GET("lookup_all_teams.php")
    suspend fun getTeams(@Query("id") id: String): TeamResponse

    @GET("lookupteam.php")
    suspend fun getDetailTeams(@Query("id") id: String): TeamResponse

    @GET("lookuptable.php")
    suspend fun getKlasemens(@Query("l") id: String): KlasemenResponse
}