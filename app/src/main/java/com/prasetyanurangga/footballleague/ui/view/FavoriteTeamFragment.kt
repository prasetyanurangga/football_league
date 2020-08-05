package com.prasetyanurangga.footballleague.ui.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.factory.FootballViewModelFactory
import com.prasetyanurangga.footballleague.data.factory.LocalViewModelFactory
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.KlasemenModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.EventAdapter
import com.prasetyanurangga.footballleague.ui.adapter.EventLocalAdapter
import com.prasetyanurangga.footballleague.ui.adapter.KlasemenAdapter
import com.prasetyanurangga.footballleague.ui.adapter.TeamLocalAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.ui.viewmodel.LocalViewModel
import com.prasetyanurangga.footballleague.utils.Status

class FavoriteTeamFragment() : Fragment() {

    private lateinit var teamViewModel: LocalViewModel
    lateinit var listEvent : RecyclerView
    lateinit var txtNotFound : TextView
    lateinit var progressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProgressDialog()
        listEvent =  view.findViewById(R.id.list_event)
        txtNotFound =  view.findViewById(R.id.txt_not_found)
        createViewModel()
        setListEvent(activity)

    }

    private fun createViewModel()
    {
        val localDB = RoomBuilder.getInstance(context!!)
        teamViewModel = ViewModelProvider(this, LocalViewModelFactory(localDB?.localDao()!!)).get(LocalViewModel::class.java)
    }

    private fun setListEvent(fragmentActivity: FragmentActivity?) {
        teamViewModel.getTeams().observe(fragmentActivity!!, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        txtNotFound.visibility = View.VISIBLE
                        listEvent.visibility = View.GONE
                        resource.data?.let { teams ->
                            updateUI(teams, context)
                            listEvent.visibility = View.VISIBLE
                            txtNotFound.visibility = View.GONE
                        }
                        progressDialog.dismiss()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()
                    }
                    else -> {
                        progressDialog.show()
                    }
                }
            }
        })
    }

    private fun updateUI(teamModel: List<TeamModel>, context: Context?)
    {
        listEvent.layoutManager = LinearLayoutManager(context)
        listEvent.adapter = TeamLocalAdapter(
            context!!,
            teamModel
        )

    }
    fun setProgressDialog()
    {
        val builder =
            AlertDialog.Builder(context!!)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress)
        progressDialog = builder.create()

    }
}
