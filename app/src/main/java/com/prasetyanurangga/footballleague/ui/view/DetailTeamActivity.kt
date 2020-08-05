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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.factory.LocalViewModelFactory
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.ViewPagerAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.ui.viewmodel.LocalViewModel
import com.prasetyanurangga.footballleague.utils.Convert
import com.prasetyanurangga.footballleague.utils.Status
import com.squareup.picasso.Picasso


class DetailTeamActivity : AppCompatActivity() {

    private lateinit var eventViewModel: FootballViewModel
    private lateinit var localViewModel: LocalViewModel
    private var isLocal:Boolean = false
    private lateinit var currentData : TeamModel
    private lateinit var btn_fav : ImageView
    private var isFav : Boolean = false
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        val idTeam = intent.getStringExtra("idTeam")
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
                localViewModel.deleteTeam(currentData).observe(this, Observer {
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
                localViewModel.saveTeam(currentData).observe(this, Observer {
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
            localViewModel.getTeamByUid(id!!).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            progressDialog.dismiss()
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
            eventViewModel.getDetailTeam(id!!).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            progressDialog.dismiss()
                            resource.data?.let { teams ->
                                updateUI(teams)
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

    private fun updateUI(teamModels: List<TeamModel>)
    {
        val txt_team = findViewById<TextView>(R.id.team_name)
        val txt_league = findViewById<TextView>(R.id.team_league)
        val txt_decs = findViewById<TextView>(R.id.team_desc)
        val txt_formed = findViewById<TextView>(R.id.team_formed)
        val txt_stadium = findViewById<TextView>(R.id.team_stadium)
        val img_team = findViewById<ImageView>(R.id.team_img)

        teamModels.forEach {teamModel ->

            txt_team.text = teamModel.NameTeam
            txt_league.text = teamModel.LeagueTeam
            txt_formed.text = teamModel.FormedYearTeam
            txt_decs.text = teamModel.DescTeam
            txt_stadium.text = teamModel.StadiumTeam
            Picasso.with(this).load(teamModel.LogoUri).into(img_team)

            currentData = teamModel

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

    fun checkFavorite(id: String)
    {
        localViewModel.getCountTeam(id).observe(this, Observer {
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
            AlertDialog.Builder(this@DetailTeamActivity)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress)
        progressDialog = builder.create()

    }
}
