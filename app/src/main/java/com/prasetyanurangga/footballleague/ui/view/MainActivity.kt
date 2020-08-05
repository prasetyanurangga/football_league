package com.prasetyanurangga.footballleague.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        findViewById<ImageView>(R.id.btn_favorite).setOnClickListener {
            val intent: Intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

    }
}
