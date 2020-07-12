package com.prasetyanurangga.footballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prasetyanurangga.footballleague.adapter.footballAdapter
import com.prasetyanurangga.footballleague.data.footballData
import com.prasetyanurangga.footballleague.model.footballModel
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    lateinit var footballDatas:  List<footballModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        footballDatas = footballData.getData()
        verticalLayout{
            recyclerView {
                lparams(width = matchParent, height =  matchParent)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = footballAdapter(this@MainActivity, footballDatas)
            }
        }

    }
}
