package com.prasetyanurangga.footballleague.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
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

class LastNextMatchFragment(private val idLeague: String?) : Fragment() {

    lateinit var listLastEvent : RecyclerView
    lateinit var listNextEvent: RecyclerView
    private lateinit var eventViewModel: FootballViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listLastEvent =  view.findViewById(R.id.list_event_last)
        listNextEvent =  view.findViewById(R.id.list_event_next)
        createViewModel()
        setListLastEvent(idLeague, activity)
        setListNextEvent(idLeague, activity)

    }

    private fun createViewModel()
    {
        eventViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(
            FootballViewModel::class.java)
    }

    private fun setListLastEvent(id: String?, fragmentActivity: FragmentActivity?) {
        eventViewModel.getEvent(id!!).observe(fragmentActivity!!, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
//                        progressDialog.hide()
                        Log.e("daa","data gak error")
                        resource.data?.let { events ->

                            Log.e("daa",events.toString())
                            updateUILast(events, context)
                        }
                    }
                    Status.ERROR -> {
//                        progressDialog.hide()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
//                        progressDialog.show()
                    }
                }
            }
        })
    }
    private fun setListNextEvent(id: String?, fragmentActivity: FragmentActivity?) {
        eventViewModel.getEventNext(id!!).observe(fragmentActivity!!, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
//                        progressDialog.hide()
                        Log.e("daa","data gak error")
                        resource.data?.let { events ->

                            Log.e("daa",events.toString())
                            updateUINext(events, context)
                        }
                    }
                    Status.ERROR -> {
//                        progressDialog.hide()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
//                        progressDialog.show()
                    }
                }
            }
        })
    }

    private fun updateUILast(eventModel: List<EventModel>, context: Context?)
    {
        val horizontalLayoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        listLastEvent.layoutManager = horizontalLayoutManager
        listLastEvent.adapter = EventAdapter(
            context!!,
            eventModel
        )
    }
    private fun updateUINext(eventModel: List<EventModel>, context: Context?)
    {
        val horizontalLayoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        listNextEvent.layoutManager = horizontalLayoutManager
        listNextEvent.adapter = EventAdapter(
            context!!,
            eventModel
        )
    }
}
