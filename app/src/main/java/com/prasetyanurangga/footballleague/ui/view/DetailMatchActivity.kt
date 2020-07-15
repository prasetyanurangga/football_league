package com.prasetyanurangga.footballleague.ui.view

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.EventAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.utils.Status
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DetailMatchActivity : AppCompatActivity() {

    private lateinit var eventViewModel: FootballViewModel
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        val idTeam = intent.getStringExtra("idEvent")

        findViewById<ImageView>(R.id.btn_go_back).setOnClickListener {
            onBackPressed()
        }
        setProgressDialog()
        createViewModel()
        setListEvent(idTeam)
    }

    private fun createViewModel()
    {
        eventViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(
            FootballViewModel::class.java)
    }

    private fun setListEvent(id: String?) {
        eventViewModel.getEventDetails(id!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { events ->
                            updateUI(events)
                        }
                        progressDialog.hide()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        progressDialog.hide()
                    }
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                }
            }
        })
    }

    private fun setLogoTeam(id: String?, imageView: ImageView){
        eventViewModel.getDetailTeam(id!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { teams ->
                            teams.forEach { team ->
                                Picasso.with(this).load(team.LogoUri).into(imageView)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        imageView.setImageResource(R.drawable.trophy)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun updateUI(eventModels: List<EventModel>)
    {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
        val txt_home = findViewById<TextView>(R.id.event_home)
        val txt_away = findViewById<TextView>(R.id.event_away)
        val txt_home_skor = findViewById<TextView>(R.id.event_home_skor)
        val txt_away_skor = findViewById<TextView>(R.id.event_away_skor)
        val txt_formation_home = findViewById<TextView>(R.id.event_home_formation)
        val txt_formation_away = findViewById<TextView>(R.id.event_away_formation)
        val img_home = findViewById<ImageView>(R.id.event_img_home)
        val img_away = findViewById<ImageView>(R.id.event_img_away)
        val txt_date = findViewById<TextView>(R.id.event_time)
        val txt_name = findViewById<TextView>(R.id.event_name)
        val txt_shot_home = findViewById<TextView>(R.id.event_home_shot)
        val txt_shot_away = findViewById<TextView>(R.id.event_away_shot)
        val txt_goal_home = findViewById<TextView>(R.id.event_home_goal)
        val txt_goal_away = findViewById<TextView>(R.id.event_away_goal)
        val txt_red_home = findViewById<TextView>(R.id.event_home_red)
        val txt_red_away = findViewById<TextView>(R.id.event_away_red)
        val txt_yellow_home = findViewById<TextView>(R.id.event_home_yellow)
        val txt_yellow_away = findViewById<TextView>(R.id.event_away_yellow)

        eventModels.forEach {eventModel ->

            val myDate: Date = simpleDateFormat.parse(eventModel.DateEvent+" "+eventModel.TimeEvent)
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss z", Locale.getDefault())
            txt_name.text = eventModel.NameLeague
            txt_date.text = formatter.format(myDate)
            txt_home.text = eventModel.HomeTeam
            txt_away.text = eventModel.AwayTeam
            txt_formation_home.text = if  (eventModel.HomeFormation.isNullOrEmpty()) "-" else eventModel.HomeFormation
            txt_formation_away.text = if  (eventModel.AwayFormation.isNullOrEmpty()) "-" else eventModel.AwayFormation
            txt_home_skor.text = if  (eventModel.HomeScore.isNullOrEmpty()) "-" else eventModel.HomeScore
            txt_away_skor.text = if  (eventModel.AwayScore.isNullOrEmpty()) "-" else eventModel.AwayScore
            txt_shot_home.text = if  (eventModel.HomeShot.isNullOrEmpty()) "-" else eventModel.HomeShot
            txt_shot_away.text = if  (eventModel.AwayShot.isNullOrEmpty()) "-" else eventModel.AwayShot
            txt_goal_home.text = if  (eventModel.HomeGoal.isNullOrEmpty()) "-" else eventModel.HomeGoal
            txt_goal_away.text = if  (eventModel.AwayGoal.isNullOrEmpty()) "-" else eventModel.AwayGoal
            txt_red_home.text = if  (eventModel.HomeRed.isNullOrEmpty()) "-" else eventModel.HomeRed
            txt_red_away.text = if  (eventModel.AwayRed.isNullOrEmpty()) "-" else eventModel.AwayRed
            txt_yellow_home.text = if  (eventModel.HomeYellow.isNullOrEmpty()) "-" else eventModel.HomeYellow
            txt_yellow_away.text = if  (eventModel.AwayYellow.isNullOrEmpty()) "-" else eventModel.AwayYellow
            setLogoTeam(eventModel.IdHome, img_home)
            setLogoTeam(eventModel.IdAway, img_away)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu?.findItem(R.id.appSearchBar)
        val searchView = search?.actionView as android.widget.SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener( object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                setListEvent(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        } )

        return super.onCreateOptionsMenu(menu)
    }

    fun setProgressDialog()
    {
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Please Wait ...")

    }
}
