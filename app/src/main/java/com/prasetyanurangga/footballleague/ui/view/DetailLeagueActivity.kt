package com.prasetyanurangga.footballleague.ui.view

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.utils.Status
import com.squareup.picasso.Picasso

class DetailLeagueActivity : AppCompatActivity(){

    private lateinit var leagueViewModel: FootballViewModel

    lateinit var txt_name : TextView
    lateinit var txt_desc : TextView
    lateinit var txt_est : TextView
    lateinit var txt_fre : TextView
    lateinit var txt_cs : TextView
    lateinit var txt_l : TextView
    lateinit var img_fb : ImageView
    lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        createViewModel()
        setProgressDialog()
        val idLeague = intent.getStringExtra("idLeague")

        txt_name = findViewById<TextView>(R.id.football_name)
        txt_desc = findViewById<TextView>(R.id.football_desc)
        txt_est = findViewById<TextView>(R.id.football_est)
        txt_fre = findViewById<TextView>(R.id.football_fre)
        txt_cs = findViewById<TextView>(R.id.football_cs)
        txt_l = findViewById<TextView>(R.id.football_l)
        img_fb = findViewById<ImageView>(R.id.football_image)

        setDetailLeague(idLeague!!)

        findViewById<ImageView>(R.id.football_go_back).setOnClickListener {
            onBackPressed()
        }
    }

    private fun createViewModel()
    {
        leagueViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(FootballViewModel::class.java)
    }

    private fun setDetailLeague(id: String) {
        leagueViewModel.getLeague(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressDialog.dismiss()
                        resource.data?.let { leagues -> updateUI(leagues) }
                    }
                    Status.ERROR -> {
                        progressDialog.dismiss()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        progressDialog.show()
                    }
                }
            }
        })
    }

    private fun updateUI(leagueModels: List<LeagueModel>)
    {
        leagueModels.forEach {leagueModel ->
            txt_name.text = leagueModel.NameLeague
            txt_desc.text = leagueModel.Description
            txt_est.text = leagueModel.FormedYear
            txt_fre.text = leagueModel.FistEvent
            txt_cs.text = leagueModel.CurrentSeason
            txt_l.text = leagueModel.Country
            Picasso.with(this).load(leagueModel.LogoUri).into(img_fb)
        }
    }

    fun setProgressDialog()
    {
        val builder =
            AlertDialog.Builder(this@DetailLeagueActivity)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress)
        progressDialog = builder.create()

    }
}
