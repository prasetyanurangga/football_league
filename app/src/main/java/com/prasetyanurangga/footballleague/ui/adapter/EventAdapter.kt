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
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.ui.view.DetailMatchActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EventAdapter (
    val context: Context,
    val items: List<EventModel>) : RecyclerView.Adapter<EventAdapter.footBallViewHolder>(){


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val eventName: TextView = view.findViewById(R.id.event_name)
        private  val eventHomeImg: ImageView = view.findViewById(R.id.event_img_1)
        private  val eventAwayImg: ImageView = view.findViewById(R.id.event_img_2)
        private val eventHomeName: TextView = view.findViewById(R.id.event_home)
        private val eventAwayName: TextView = view.findViewById(R.id.event_away)
        private val eventHomeSkor: TextView = view.findViewById(R.id.event_home_skor)
        private val eventAwaySkor: TextView = view.findViewById(R.id.event_away_skor)
        private val eventLeague: TextView = view.findViewById(R.id.event_league)
        private val eventTime: TextView = view.findViewById(R.id.event_time)

        fun bindItem(items: EventModel){
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
            val myDate: Date = simpleDateFormat.parse(items.DateEvent+" "+items.TimeEvent)
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss z", Locale.getDefault())

            eventName.text = items.NameEvent
            eventHomeName.text = items.HomeTeam
            eventAwayName.text = items.AwayTeam
            eventHomeSkor.text = if  (items.HomeScore.isNullOrEmpty()) "-" else items.HomeScore
            eventAwaySkor.text = if  (items.AwayScore.isNullOrEmpty()) "-" else items.AwayScore
            eventLeague.text = items.NameLeague
            eventTime.text = formatter.format(myDate)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): footBallViewHolder {
        return footBallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        )
    }



    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: footBallViewHolder, position: Int) {
        holder.bindItem(items[position])
        holder.itemView.setOnClickListener { view ->
            val intent: Intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra("idEvent", items[position].IdEvent)
            context.startActivity(intent)

        }
    }
}