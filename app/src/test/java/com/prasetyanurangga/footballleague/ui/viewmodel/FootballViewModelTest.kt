package com.prasetyanurangga.footballleague.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.prasetyanurangga.footballleague.TestCoroutineRule
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.view.DetailLeagueActivity
import com.prasetyanurangga.footballleague.utils.Resource
import com.prasetyanurangga.footballleague.utils.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class FootballViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()



    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var view: DetailLeagueActivity


    lateinit var apiRepository: ApiRepository

    lateinit var viewModel: FootballViewModel

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

    private val dummyTeam: TeamModel = TeamModel(
        IdTeam = "133602",
        NameTeam = 	"Liverpool",
        LogoUri = 	"https://www.thesportsdb.com/images/media/team/badge/uvxuqq1448813372.png"

    )

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        apiRepository = mock(ApiRepository(RetrofitBuilder.apiService)::class.java);
        viewModel = FootballViewModel(apiRepository);
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun getLeague() {
        runBlocking {
            launch(Dispatchers.IO){
                val listDummy = listOf<LeagueModel>(dummyLeague)
                `when`(apiRepository.getLeagues("1")).thenReturn(listDummy)
                val result = viewModel.getLeague("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    @Test
    fun getSearchEvent()  {
        runBlocking{
            launch(Dispatchers.IO){
                val listDummy= listOf<EventModel>(dummyEvent)
                `when`(apiRepository.getSearchEvents("1")).thenReturn(listDummy)
                val result = viewModel.getSearchEvent("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    @Test
    fun getEventDetails() {
        runBlocking{
            launch(Dispatchers.IO){
                val listDummy    = listOf<EventModel>(dummyEvent)
                `when`(apiRepository.getEventDetails("1")).thenReturn(listDummy)
                val result = viewModel.getEventDetails("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    @Test
    fun getEvent() {
        runBlocking{
            launch(Dispatchers.IO){
                val listDummy    = listOf<EventModel>(dummyEvent)
                `when`(apiRepository.getEvents("1")).thenReturn(listDummy)
                val result = viewModel.getEvent("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    @Test
    fun getEventNext() {
        runBlocking {
            launch(Dispatchers.IO){
                val listDummy    = listOf<EventModel>(dummyEvent)
                `when`(apiRepository.getEventNexts("1")).thenReturn(listDummy)
                val result = viewModel.getEventNext("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    @Test
    fun getTeam(){
        runBlocking{
            launch(Dispatchers.IO){
                val listDummy    = listOf<TeamModel>(dummyTeam)
                `when`(apiRepository.getTeams("1")).thenReturn(listDummy)
                val result = viewModel.getTeam("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    @Test
    fun getDetailTeam() {
        runBlocking{
            launch(Dispatchers.IO){
                val listDummy    = listOf<TeamModel>(dummyTeam)
                `when`(apiRepository.getDetailTeams("1")).thenReturn(listDummy)
                val result = viewModel.getDetailTeam("1")
                assert(Resource(status = Status.SUCCESS, data = listDummy, message = null) == result.getOrAwaitValue())
            }
        }
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}