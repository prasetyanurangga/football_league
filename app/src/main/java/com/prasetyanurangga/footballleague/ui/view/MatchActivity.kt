package com.prasetyanurangga.footballleague.ui.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.TeamAdapter
import com.prasetyanurangga.footballleague.ui.adapter.ViewPagerAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.utils.Status
import com.squareup.picasso.Picasso

class MatchActivity : AppCompatActivity() {

    lateinit var listTeam : RecyclerView
    private lateinit var teamViewModel: FootballViewModel
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)


        val idLeague = intent.getStringExtra("idLeague")

        val sectionsPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        sectionsPagerAdapter.populateFragment(LastMatchFragment(idLeague), "Last Match", this)
        sectionsPagerAdapter.populateFragment(NextMatchFragment(idLeague), "Next Match", this)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        setProgressDialog()
        listTeam =  findViewById(R.id.list_team)
        createViewModel()
        setListTeam(idLeague!!)


    }

    private fun createViewModel()
    {
        teamViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(
            FootballViewModel::class.java)
    }

    private fun setListTeam(id: String) {
        teamViewModel.getTeam(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
//                        progressDialog.hide()
                        Log.e("daa","data gak error")
                        resource.data?.let { teams ->

                            Log.e("daa",teams.toString())
                            updateUI(teams)
                        }
                    }
                    Status.ERROR -> {
//                        progressDialog.hide()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        progressDialog.show()
                    }
                }
            }
        })

        teamViewModel.getLeague(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressDialog.hide()
                        resource.data?.let { leagues -> updateLeague(leagues) }
                    }
                    Status.ERROR -> {
                        progressDialog.hide()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                }
            }
        })
    }

    private fun updateUI(teamModels: List<TeamModel>)
    {
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listTeam.layoutManager = horizontalLayoutManager
        listTeam.adapter = TeamAdapter(
            this,
            teamModels
        )
    }

    private fun updateLeague(leagueModels: List<LeagueModel>)
    {
        val txt_name = findViewById<TextView>(R.id.football_name)
        val txt_desc = findViewById<TextView>(R.id.football_desc)
        val img_fb = findViewById<ImageView>(R.id.football_image)
        leagueModels.forEach {leagueModel ->
            Log.e("Image",leagueModel.LogoUri )
            txt_name.setText(leagueModel.NameLeague)
            txt_desc.setText(leagueModel.Description.toString().subSequence(0,100))
            Picasso.with(this).load(leagueModel.LogoUri).into(img_fb)
        }
    }
    fun setProgressDialog()
    {
        progressDialog = ProgressDialog(this@MatchActivity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Please Wait ...")

    }




}