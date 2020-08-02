package com.prasetyanurangga.footballleague.data.repository

import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.service.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ApiRepositoryTest {

    private val idLeague = "4328"
    private val idEvent = "441613"
    private val idTeam = "133602"
    private val searchKeyword = "arsenal"

    private lateinit var apiService: ApiService
    private val dummyLeague: LeagueModel = LeagueModel(
        IdLeague = "4328",
        NameLeague = "English Premier League",
        AltNameLeague = "Premier League",
        CurrentSeason = "1920",
        FormedYear = 	"1992",
        Description = "The Premier League (often referred to as the English Premier League (EPL) outside England), is the top level of the English football league system. Contested by 20 clubs, it operates on a system of promotion and relegation with the English Football League (EFL).\r\n\r\nThe Premier League is a corporation in which the member clubs act as shareholders. Seasons run from August to May with each team playing 38 matches (playing each other home and away). Most games are played on Saturday and Sunday afternoons. The Premier League has featured 47 English and two Welsh clubs since its inception, making it a cross-border league.\r\n\r\nThe competition was formed as the FA Premier League on 20 February 1992 following the decision of clubs in the Football League First Division to break away from the Football League, founded in 1888, and take advantage of a lucrative television rights deal. The deal was worth £1 billion a year domestically as of 2013–14, with BSkyB and BT Group securing the domestic rights to broadcast 116 and 38 games respectively. The league generates €2.2 billion per year in domestic and international television rights. In 2014–15, teams were apportioned revenues of £1.6 billion, rising sharply to £2.4 billion in 2016–17.\r\n\r\nThe Premier League is the most-watched sports league in the world, broadcast in 212 territories to 643 million homes and a potential TV audience of 4.7 billion people. In the 2014–15 season, the average Premier League match attendance exceeded 36,000, second highest of any professional football league behind the Bundesliga's 43,500. Most stadium occupancies are near capacity. The Premier League ranks second in the UEFA coefficients of leagues based on performances in European competitions over the past five seasons, as of 2018.\r\n\r\nForty-nine clubs have competed since the inception of the Premier League in 1992. Six of them have won the title: Manchester United (13), Chelsea (5), Arsenal (3), Manchester City (3), Blackburn Rovers (1), and Leicester City (1). Following the 2003–04 season, Arsenal acquired the nickname \"The Invincibles\" as they became, and still remain, the only club to complete a Premier League campaign without losing a single game. The record of most points in a season is 100 by Manchester City in 2017–18.",
        LogoUri = 	"https://www.thesportsdb.com/images/media/league/badge/i6o0kh1549879062.png",
        Country = "England",
        FistEvent = "2000-08-18"
    )

    private val dummyEvent: EventModel = EventModel(
        IdEvent = 	"441613",
        NameEvent = "Liverpool vs Swansea",
        NameLeague = "English Premier League",
        HomeTeam = 	"Liverpool",
        AwayTeam = "Swansea",
        HomeScore =  "4",
        AwayScore = "1",
        TimeEvent = "20:00:00+00:00",
        DateEvent = "2014-12-29",
        HomeFormation = "3-4-3",
        AwayFormation = "4-2-3-1",
        IdHome = 	"133602",
        IdAway = 	"133614",
        HomeShot ="11",
        AwayShot = "4",
        HomeGoal = "69':Own  Jonjo Shelvey;61': Adam Lallana;51': Adam Lallana;33': Alberto Moreno;",
        AwayGoal = "52': Gylfi Sigurdsson;",
        HomeRed = "",
        AwayRed = "",
        HomeYellow = "49': Martin Skrtel;",
        AwayYellow = "",
        IdLeague = "4328",
        SportName = "Soccer",
        ImgHome = null,
        ImgAway = null
    )

    private val dummyTeam: TeamModel = TeamModel(
        IdTeam = "133602",
        NameTeam = 	"Liverpool",
        LogoUri = 	"https://www.thesportsdb.com/images/media/team/badge/uvxuqq1448813372.png"

    )

    private val dummyCountEvent: Int = 1
    private val dummyCountTeam: Int = 1

    @Before
    fun setUp(){
        apiService = RetrofitBuilder.apiService
    }


    @Test
    fun getLeagues() {
        val result = runBlocking {
            apiService.getLeagues(idLeague).items[0]
        }

        assertNotNull(result)
        assertEquals(dummyLeague.IdLeague, result.IdLeague)
        assertEquals(dummyLeague.NameLeague, result.NameLeague)
        assertEquals(dummyLeague.Description, result.Description)
        assertEquals(dummyLeague.FormedYear, result.FormedYear)
        assertEquals(dummyLeague.AltNameLeague, result.AltNameLeague)
        assertEquals(dummyLeague.CurrentSeason, result.CurrentSeason)
        assertEquals(dummyLeague.LogoUri, result.LogoUri)
        assertEquals(dummyLeague.Country, result.Country)
        assertEquals(dummyLeague.FistEvent, result.FistEvent)
    }

    @Test
    fun getEvents() {
        val result = runBlocking {
            apiService.getEvents(idLeague).items
        }
        assertNotNull(result)
        assertTrue("Past Event Least Then Zero", result.size >= dummyCountEvent)
    }

    @Test
    fun getEventNexts() {
        val result = runBlocking {
            apiService.getEventNexts(idLeague).items
        }
        assertNotNull(result)
        assertTrue("Next Event Least Then Zero", result.size >= dummyCountEvent)
    }

    @Test
    fun getEventDetails() {
        val result = runBlocking {
            apiService.getEventDetails(idEvent).items[0]
        }
        assertNotNull(result)
        assertEquals(dummyEvent.IdEvent, result.IdEvent)
        assertEquals(dummyEvent.NameEvent, result.NameEvent)
        assertEquals(dummyEvent.NameLeague, result.NameLeague)
        assertEquals(dummyEvent.HomeTeam, result.HomeTeam)
        assertEquals(dummyEvent.AwayTeam, result.AwayTeam)
        assertEquals(dummyEvent.HomeScore, result.HomeScore)
        assertEquals(dummyEvent.AwayScore, result.AwayScore)
        assertEquals(dummyEvent.TimeEvent, result.TimeEvent)
        assertEquals(dummyEvent.DateEvent, result.DateEvent)
        assertEquals(dummyEvent.HomeFormation, result.HomeFormation)
        assertEquals(dummyEvent.AwayFormation, result.AwayFormation)
        assertEquals(dummyEvent.IdHome, result.IdHome)
        assertEquals(dummyEvent.IdAway, result.IdAway)
        assertEquals(dummyEvent.HomeShot, result.HomeShot)
        assertEquals(dummyEvent.AwayShot, result.AwayShot)
        assertEquals(dummyEvent.HomeGoal, result.HomeGoal)
        assertEquals(dummyEvent.AwayGoal, result.AwayGoal)
        assertEquals(dummyEvent.HomeRed, result.HomeRed)
        assertEquals(dummyEvent.AwayRed, result.AwayRed)
        assertEquals(dummyEvent.HomeYellow, result.HomeYellow)
        assertEquals(dummyEvent.AwayYellow, result.AwayYellow)
        assertEquals(dummyEvent.IdLeague, result.IdLeague)
        assertEquals(dummyEvent.SportName, result.SportName)
        assertEquals(dummyEvent.ImgHome, result.ImgHome)
        assertEquals(dummyEvent.ImgAway, result.ImgAway)
    }

    @Test
    fun getSearchEvents() {
        val result = runBlocking {
            apiService.getSearchEvents(searchKeyword).items
        }
        assertNotNull(result)
        assertTrue("Search Event Least Then Zero", result.size >= dummyCountEvent)
    }

    @Test
    fun getTeams() {
        val result = runBlocking {
            apiService.getTeams(idLeague).items
        }
        assertNotNull(result)
        assertTrue("Team Least Then Zero", result.size >= dummyCountTeam)
    }

    @Test
    fun getDetailTeams() {
        val result = runBlocking {
            apiService.getDetailTeams(idTeam).items[0]
        }
        assertNotNull(result)
        assertEquals(dummyTeam.IdTeam, result.IdTeam)
        assertEquals(dummyTeam.NameTeam, result.NameTeam)
        assertEquals(dummyTeam.LogoUri, result.LogoUri)
    }
}