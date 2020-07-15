package com.prasetyanurangga.footballleague.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.model.FootballModel
import com.prasetyanurangga.footballleague.ui.view.MatchActivity

class FootballAdapter (
    val context: Context,
    val items: List<FootballModel>) : RecyclerView.Adapter<FootballAdapter.footBallViewHolder>(){


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val footBallImage: ImageView = view.findViewById(R.id.football_image)
        private val footBallName: TextView = view.findViewById(R.id.football_name)

        fun bindItem(items: FootballModel){
            footBallName.text = items.name
            footBallImage.setImageResource(items.imageDrawable)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): footBallViewHolder {
        return footBallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_league, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: footBallViewHolder, position: Int) {
        holder.bindItem(items[position])
        holder.itemView.setOnClickListener { view ->
            val intent: Intent = Intent(context, MatchActivity::class.java)
            intent.putExtra("position", position)
            context.startActivity(intent)

        }
    }
}