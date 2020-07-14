package com.prasetyanurangga.footballleague.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.ui.adapter.FootballAdapter
import com.prasetyanurangga.footballleague.data.local.FootballData
import com.prasetyanurangga.footballleague.data.model.FootballModel

class MainActivity : AppCompatActivity() {

    lateinit var footballDatas:  List<FootballModel>
    lateinit var listLeague : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        footballDatas = FootballData.getData()

        listLeague =  findViewById(R.id.list_league)
        listLeague.layoutManager = GridLayoutManager(this@MainActivity, 2)
        listLeague.adapter = FootballAdapter(
            this@MainActivity,
            footballDatas
        )

    }
}
