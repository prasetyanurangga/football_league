package com.prasetyanurangga.footballleague.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.ui.view.DetailActivity
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.data.model.footballModel

class footballAdapter (
    val context: Context,
    val items: List<footballModel>) : RecyclerView.Adapter<footballAdapter.footBallViewHolder>(){


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val footBallImage: ImageView = view.findViewById(R.id.football_image)
        private val footBallName: TextView = view.findViewById(R.id.football_name)

        fun bindItem(items: footballModel){
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
            val intent: Intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("position", position)
            context.startActivity(intent)

        }
    }
}