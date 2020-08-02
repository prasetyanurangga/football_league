package com.prasetyanurangga.footballleague.ui.view

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.factory.LocalViewModelFactory
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.ui.viewmodel.LocalViewModel
import com.prasetyanurangga.footballleague.utils.Convert
import com.prasetyanurangga.footballleague.utils.Status
import com.squareup.picasso.Picasso


class DetailMatchActivity : AppCompatActivity() {

    private lateinit var eventViewModel: FootballViewModel
    private lateinit var localViewModel: LocalViewModel
    private var isLocal:Boolean = false
    private lateinit var currentData : EventModel
    private lateinit var btn_fav : ImageView
    private var isFav : Boolean = false
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        val idTeam = intent.getStringExtra("idEvent")
        isLocal = intent.getBooleanExtra("isLocal", false)

        findViewById<ImageView>(R.id.btn_go_back).setOnClickListener {
            onBackPressed()
        }

        setProgressDialog()
        createViewModel()
        setListEvent(idTeam)

        btn_fav = findViewById<ImageView>(R.id.btn_favorite)


        checkFavorite(idTeam!!)

        btn_fav.setOnClickListener {view->
            if (isFav)
            {
                localViewModel.deleteEvent(currentData).observe(this, Observer {
                    it?.let { resource ->
                        when(resource.status){
                            Status.SUCCESS-> {
                                btn_fav.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
                                Toast.makeText(this, "Success Delete Favorite", Toast.LENGTH_LONG).show()
                            }
                            Status.ERROR ->{
                                Toast.makeText(this, "Failed Delete Favorite", Toast.LENGTH_LONG).show()
                            }
                            else -> {

                            }
                        }
                    }
                })


                isFav = false
            }
            else if (!isFav)
            {
                localViewModel.saveEvent(currentData).observe(this, Observer {
                    it?.let { resource ->
                        when(resource.status){
                            Status.SUCCESS-> {
                                btn_fav.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
                                Toast.makeText(this, "Success Add Favorite", Toast.LENGTH_LONG).show()
                            }
                            Status.ERROR ->{

                                Toast.makeText(this, "Failed Add Favorite", Toast.LENGTH_LONG).show()
                            }
                            else -> {

                            }
                        }
                    }
                })


                isFav = true
            }
        }

    }

    private fun createViewModel()
    {
        val localDB = RoomBuilder.getInstance(this)
        localViewModel = ViewModelProvider(this, LocalViewModelFactory(localDB?.localDao()!!)).get(LocalViewModel::class.java)
        eventViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(
            FootballViewModel::class.java)
    }

    private fun setListEvent(id: String?) {
        if(isLocal)
        {
            localViewModel.getEventByUid(id!!).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { events ->
                                updateUI(events)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            progressDialog.hide()
                        }
                        else -> {
                            progressDialog.show()
                        }
                    }
                }
            })
        }
        else
        {
            eventViewModel.getEventDetails(id!!).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { events ->
                                updateUI(events)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()
                        }
                        else -> {
                            progressDialog.show()
                        }
                    }
                }
            })
        }

    }

    private fun setLogoTeam(id: String?, imageView: ImageView): String{
        var uriImage = ""
        eventViewModel.getDetailTeam(id!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { teams ->
                            teams.forEach { team ->
                                Picasso.with(this).load(team.LogoUri).into(imageView)
                                uriImage = team.LogoUri
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        imageView.setImageResource(R.drawable.trophy)
                    }
                    else -> {
                    }
                }
            }
        })

        return uriImage
    }

    private fun updateUI(eventModels: List<EventModel>)
    {
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

            txt_name.text = eventModel.NameLeague
            txt_date.text = Convert.convertDateLocalGTM(time = eventModel.TimeEvent,date = eventModel.DateEvent)
            txt_home.text = eventModel.HomeTeam
            txt_away.text = eventModel.AwayTeam

            if  (eventModel.HomeFormation.isNullOrEmpty()) eventModel.HomeFormation = "-" else  eventModel.HomeFormation
            if  (eventModel.AwayFormation.isNullOrEmpty()) eventModel.AwayFormation = "-" else eventModel.AwayFormation
            if  (eventModel.HomeScore.isNullOrEmpty()) eventModel.HomeScore = "-" else eventModel.HomeScore
            if  (eventModel.AwayScore.isNullOrEmpty()) eventModel.AwayScore = "-" else eventModel.AwayScore
            if  (eventModel.HomeShot.isNullOrEmpty()) eventModel.HomeShot = "-" else eventModel.HomeShot
            if  (eventModel.AwayShot.isNullOrEmpty()) eventModel.AwayShot = "-" else eventModel.AwayShot
            if  (eventModel.HomeGoal.isNullOrEmpty()) eventModel.HomeGoal = "-" else eventModel.HomeGoal
            if  (eventModel.AwayGoal.isNullOrEmpty()) eventModel.AwayGoal = "-" else eventModel.AwayGoal
            if  (eventModel.HomeRed.isNullOrEmpty()) eventModel.HomeRed = "-" else eventModel.HomeRed
            if  (eventModel.AwayRed.isNullOrEmpty()) eventModel.AwayRed = "-" else eventModel.AwayRed
            if  (eventModel.HomeYellow.isNullOrEmpty()) eventModel.HomeYellow = "-" else eventModel.HomeYellow
            if  (eventModel.AwayYellow.isNullOrEmpty()) eventModel.AwayYellow = "-" else eventModel.AwayYellow

            txt_formation_home.text = eventModel.HomeFormation
            txt_formation_away.text = eventModel.AwayFormation
            txt_home_skor.text = eventModel.HomeScore
            txt_away_skor.text = eventModel.AwayScore
            txt_shot_home.text = eventModel.HomeShot
            txt_shot_away.text = eventModel.AwayShot
            txt_goal_home.text = eventModel.HomeGoal
            txt_goal_away.text = eventModel.AwayGoal
            txt_red_home.text = eventModel.HomeRed
            txt_red_away.text = eventModel.AwayRed
            txt_yellow_home.text = eventModel.HomeYellow
            txt_yellow_away.text = eventModel.AwayYellow
            eventModel.ImgHome = setLogoTeam(eventModel.IdHome, img_home)
            eventModel.ImgAway = setLogoTeam(eventModel.IdAway,img_away)


            currentData = eventModel

        }



        progressDialog.dismiss()

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

    fun checkFavorite(id: String)
    {
        localViewModel.getCountEvent(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data == 0)
                        {
                            btn_fav.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
                            isFav = false
                        }
                        else
                        {
                            isFav = true
                            btn_fav.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                    }
                }
            }
        })
    }

    fun setProgressDialog()
    {
        val builder =
            AlertDialog.Builder(this@DetailMatchActivity)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress)
        progressDialog = builder.create()

    }
}
