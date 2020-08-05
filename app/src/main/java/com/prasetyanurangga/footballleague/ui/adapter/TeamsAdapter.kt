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
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.ui.view.DetailMatchActivity
import com.prasetyanurangga.footballleague.ui.view.DetailTeamActivity
import com.squareup.picasso.Picasso

class TeamsAdapter (
    val context: Context,
    val items: List<TeamModel>) : RecyclerView.Adapter<TeamsAdapter.footBallViewHolder>(){


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val teamName: TextView = view.findViewById(R.id.team_name)
        private  val teamDesc: TextView = view.findViewById(R.id.team_desc)
        private  val teamLogo: ImageView = view.findViewById(R.id.team_logo)

        fun bindItem(items: TeamModel, context: Context){
            teamName.text = items.NameTeam
            teamDesc.text = if  (items.DescTeam.isNullOrEmpty()) "-" else items.DescTeam.substring(0, 40)+"..."
            Picasso.with(context).load(items.LogoUri).into(teamLogo)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): footBallViewHolder {
        return footBallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_teams, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: footBallViewHolder, position: Int) {
        holder.bindItem(items[position], context)
        holder.itemView.setOnClickListener { view ->
            val intent: Intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra("idTeam", items[position].IdTeam)
            context.startActivity(intent)
        }
    }
}