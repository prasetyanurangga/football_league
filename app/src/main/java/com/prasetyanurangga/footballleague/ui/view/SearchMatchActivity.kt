package com.prasetyanurangga.footballleague.ui.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.EventAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.utils.Status

class SearchMatchActivity : AppCompatActivity() {

    lateinit var listEvent : RecyclerView
    lateinit var txtNotFound : TextView
    private lateinit var eventViewModel: FootballViewModel
    lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        setProgressDialog()
        supportActionBar?.title = "Search Event"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        listEvent =  findViewById(R.id.list_event_search)
        txtNotFound =  findViewById(R.id.txt_not_found)
        createViewModel()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun createViewModel()
    {
        eventViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(
            FootballViewModel::class.java)
    }

    private fun setListEvent(id: String?) {
        eventViewModel.getSearchEvent(id!!).observe(this, Observer {
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
            listEvent.adapter = EventAdapter(
                this,
                eventModel
            )

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
                if(p0.isNullOrEmpty())
                {
                    txtNotFound.visibility = View.VISIBLE
                    listEvent.visibility = View.GONE
                }
                return true
            }
        } )

        return super.onCreateOptionsMenu(menu)
    }

    fun setProgressDialog()
    {
        val builder =
            AlertDialog.Builder(this@SearchMatchActivity)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress)
        progressDialog = builder.create()

    }
}

