package com.prasetyanurangga.footballleague.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.footballleague.DetailActivity
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.model.footballModel
import org.jetbrains.anko.*

class footballAdapter (
    val context: Context,
    val items: List<footballModel>) : RecyclerView.Adapter<footballAdapter.footBallViewHolder>(), AnkoLogger {

    class FootBallItemUI : AnkoComponent<ViewGroup> {
        @SuppressLint("NewApi")
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui){
                relativeLayout(){

                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(20)
                    elevation = 10f



                    imageView {
                        id = R.id.football_image
                    }.lparams {
                        height = dip(75)
                        width = dip(75)
                        centerHorizontally()
                    }

                    textView {
                        id = R.id.football_name
                        textSize = 16f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams {
                        topMargin = dip(8)
                        centerHorizontally()
                        below(R.id.football_image)
                    }

                }


            }
        }
    }


    class footBallViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private  val footBallImage: ImageView = view.find(R.id.football_image)
        private val footBallName: TextView = view.find(R.id.football_name)

        fun bindItem(items: footballModel){
            footBallName.text = items.name
            footBallImage.setImageResource(items.imageDrawable)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): footBallViewHolder {
        return footBallViewHolder(FootBallItemUI().createView(AnkoContext.create(context, parent)))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: footBallViewHolder, position: Int) {
        holder.bindItem(items[position])
        holder.itemView.setOnClickListener { view ->
            context.startActivity<DetailActivity>("position" to position)
            context.longToast("You Choose ${items[position].name}")
            info("You Choose ${items[position].name}")

        }
    }
}