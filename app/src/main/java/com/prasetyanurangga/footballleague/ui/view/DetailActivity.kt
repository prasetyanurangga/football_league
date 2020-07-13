package com.prasetyanurangga.footballleague.ui.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.local.footballData
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.model.footballModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.utils.Status
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity(){

    private lateinit var leagueViewModel: FootballViewModel

    lateinit var footballDatas : List<footballModel>
    lateinit var txt_name : TextView
    lateinit var txt_desc : TextView
    lateinit var txt_est : TextView
    lateinit var txt_fre : TextView
    lateinit var txt_cs : TextView
    lateinit var txt_l : TextView
    lateinit var img_fb : ImageView
    lateinit var btn_go_back : ImageView
    lateinit var btn_go_event : ImageView
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        this.getSupportActionBar()?.hide();

        createViewModel()
        setProgressDialog()

        footballDatas = footballData.getData()
        val position = intent.getIntExtra("position",0)




        btn_go_back = findViewById<ImageView>(R.id.football_go_back)
        btn_go_event = findViewById<ImageView>(R.id.football_go_event)
        txt_name = findViewById<TextView>(R.id.football_name)
        txt_desc = findViewById<TextView>(R.id.football_desc)
        txt_est = findViewById<TextView>(R.id.football_est)
        txt_fre = findViewById<TextView>(R.id.football_fre)
        txt_cs = findViewById<TextView>(R.id.football_cs)
        txt_l = findViewById<TextView>(R.id.football_l)
        img_fb = findViewById<ImageView>(R.id.football_image)

        setDetailLeague(footballDatas[position].id.toString())

        btn_go_back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btn_go_event.setOnClickListener {
            val intent: Intent = Intent(this, MatchActivity::class.java)
            intent.putExtra("idLeague", footballDatas[position].id.toString())
            startActivity(intent)
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
                        progressDialog.hide()
                        resource.data?.let { leagues -> updateUI(leagues) }
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

    private fun updateUI(leagueModels: List<LeagueModel>)
    {
        leagueModels.forEach {leagueModel ->
            Log.e("Image",leagueModel.LogoUri )
            txt_name.setText(leagueModel.NameLeague)
            txt_desc.setText(leagueModel.Description)
            txt_est.setText(leagueModel.FormedYear)
            txt_fre.setText(leagueModel.FistEvent)
            txt_cs.setText(leagueModel.CurrentSeason)
            txt_l.setText(leagueModel.Country)
            Picasso.with(this).load(leagueModel.LogoUri).into(img_fb)
        }
    }

    fun setProgressDialog()
    {
        progressDialog = ProgressDialog(this@DetailActivity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Please Wait ...")

    }
}
