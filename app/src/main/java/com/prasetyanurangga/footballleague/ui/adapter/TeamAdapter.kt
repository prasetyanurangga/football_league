package com.prasetyanurangga.footballleague.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.squareup.picasso.Picasso

class TeamAdapter (
    val context: Context,
    val items: List<TeamModel>) : RecyclerView.Adapter<TeamAdapter.footBallViewHolder>(){


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val teamName: TextView = view.findViewById(R.id.team_name)
        private  val teamLogo: ImageView = view.findViewById(R.id.team_logo)

        fun bindItem(items: TeamModel, context: Context){
            teamName.text = items.NameTeam
            Picasso.with(context).load(items.LogoUri).into(teamLogo)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): footBallViewHolder {
        return footBallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: footBallViewHolder, position: Int) {
        holder.bindItem(items[position], context)
//        holder.itemView.setOnClickListener { view ->
//            val intent: Intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("position", position)
//            context.startActivity(intent)
//
//        }
    }
}