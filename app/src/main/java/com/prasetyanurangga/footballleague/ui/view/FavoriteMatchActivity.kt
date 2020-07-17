package com.prasetyanurangga.footballleague.ui.view

import android.app.Dialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.factory.LocalViewModelFactory
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.EventAdapter
import com.prasetyanurangga.footballleague.ui.adapter.EventLocalAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.ui.viewmodel.LocalViewModel
import com.prasetyanurangga.footballleague.utils.Status

class FavoriteMatchActivity : AppCompatActivity() {

    lateinit var listEvent : RecyclerView
    lateinit var txtNotFound : TextView
    private lateinit var leagueViewModel: LocalViewModel
    lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_match)
        setProgressDialog()
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        listEvent =  findViewById(R.id.list_event)
        txtNotFound =  findViewById(R.id.txt_not_found)
        createViewModel()
        val idLeague = intent.getStringExtra("idLeague")
        setListEvent(idLeague)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun createViewModel()
    {
        val localDB = RoomBuilder.getInstance(this)
        leagueViewModel = ViewModelProvider(this, LocalViewModelFactory(localDB?.localDao()!!)).get(LocalViewModel::class.java)
    }

    private fun setListEvent(id: String?) {
        leagueViewModel.getEvents(id!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        txtNotFound.visibility = View.VISIBLE
                        listEvent.visibility = View.GONE
                        resource.data?.let { events ->
                            updateUI(events)
                            listEvent.visibility = View.VISIBLE
                            txtNotFound.visibility = View.GONE
                        }
                        progressDialog.dismiss()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()
                    }
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                }
            }
        })
    }

    private fun updateUI(eventModel: List<EventModel>)
    {
        listEvent.layoutManager = LinearLayoutManager(this)
        listEvent.adapter = EventLocalAdapter(
            this,
            eventModel
        )

    }

    fun setProgressDialog()
    {
        val builder =
            AlertDialog.Builder(this@FavoriteMatchActivity)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress)
        progressDialog = builder.create()

    }
}
