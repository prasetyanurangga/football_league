package com.prasetyanurangga.footballleague.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.model.KlasemenModel
import com.squareup.picasso.Picasso

class KlasemenAdapter (
    val context: Context,
    val items: List<KlasemenModel>) : RecyclerView.Adapter<KlasemenAdapter.footBallViewHolder>(){


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val teamName: TextView = view.findViewById(R.id.team_name)
        private  val teamPlay: TextView = view.findViewById(R.id.team_play)
        private  val teamWin: TextView = view.findViewById(R.id.team_win)
        private  val teamLoss: TextView = view.findViewById(R.id.team_loss)
        private  val teamTotal: TextView = view.findViewById(R.id.team_total)

        fun bindItem(items: KlasemenModel, context: Context){
            teamName.text = items.Name
            teamPlay.text = items.Played
            teamWin.text = items.Win
            teamLoss.text = items.Loss
            teamTotal.text = items.Total

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): footBallViewHolder {
        return footBallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_klasemen, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: footBallViewHolder, position: Int) {
        holder.bindItem(items[position], context)
    }
}