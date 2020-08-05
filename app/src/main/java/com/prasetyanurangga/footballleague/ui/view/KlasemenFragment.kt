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
import com.prasetyanurangga.footballleague.data.model.KlasemenModel
import com.prasetyanurangga.footballleague.data.network.RetrofitBuilder
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.adapter.EventAdapter
import com.prasetyanurangga.footballleague.ui.adapter.KlasemenAdapter
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.utils.Status

class KlasemenFragment(private val idLeague: String?) : Fragment() {

    lateinit var listEvent : RecyclerView
    private lateinit var eventViewModel: FootballViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_klasemen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listEvent =  view.findViewById(R.id.list_event)
        createViewModel()
        setListEvent(idLeague, activity)

    }

    private fun createViewModel()
    {
        eventViewModel = ViewModelProvider(this, FootballViewModelFactory(ApiRepository(RetrofitBuilder.apiService))).get(
            FootballViewModel::class.java)
    }

    private fun setListEvent(id: String?, fragmentActivity: FragmentActivity?) {
        eventViewModel.getKlasemen(id!!).observe(fragmentActivity!!, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.e("Klasemne",resource.data.toString());
                        resource.data?.let { klasemens ->
                            updateUI(klasemens, context)
                        }
                    }
                    Status.ERROR -> {
                        Log.e("Klasemne",it.message.toString())
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                    }
                }
            }
        })
    }

    private fun updateUI(klasemenModel: List<KlasemenModel>, context: Context?)
    {
        listEvent.layoutManager = LinearLayoutManager(context)
        listEvent.adapter = KlasemenAdapter(
            context!!,
            klasemenModel
        )
    }
}
