package com.prasetyanurangga.footballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.prasetyanurangga.footballleague.data.footballData
import com.prasetyanurangga.footballleague.model.footballModel
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.START
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.TOP
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.BOTTOM
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.END
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk27.coroutines.onClick

class DetailActivity : AppCompatActivity(), AnkoLogger {

    lateinit var footballDatas : List<footballModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.getSupportActionBar()?.hide();

        scrollView {
            constraintLayout {
                id = R.id.football_body

                val views = relativeLayout(){
                    lparams(width = matchParent, height = dip(200))
                    padding = dip(16)
                    id = R.id.football_back
                    backgroundColor = theme.color(R.attr.colorPrimary)

                    imageView {
                        id = R.id.football_go_back
                        padding = dip(8)
                        elevation = 10f
                        background = getDrawable(R.drawable.ic_arrow_back)
                        onClick {
                            this@DetailActivity.startActivity<MainActivity>()
                        }
                    }.lparams {
                        height = dip(20)
                        width = dip(20)
                    }

                }
                val image = imageView {
                    id = R.id.football_image
                    padding = dip(8)
                    elevation = 10f
                    background = getDrawable(R.drawable.circle)
                }.lparams {
                    height = dip(100)
                    width = dip(100)
                }
                val txt_name = textView {
                    id = R.id.football_name
                    textSize = 16f
                }.lparams {
                    margin = dip(16)
                }

                val txt_desc = textView {
                    id = R.id.football_desc
                    textSize = 16f
                }.lparams {
                    margin = dip(16)
                }
                applyConstraintSet {
                    image{
                        connect(
                            START to START of R.id.football_back margin dip(10),
                            TOP to BOTTOM of R.id.football_back margin dip(10),
                            END to END of R.id.football_back margin dip(10),
                            BOTTOM to BOTTOM of R.id.football_back
                        )
                    }

                    views{
                        connect(
                            START to START of R.id.football_body margin dip(0),
                            TOP to TOP of R.id.football_body margin dip(0),
                            END to END of R.id.football_body margin dip(0)
                        )
                    }

                    txt_name{
                        connect(
                            TOP to BOTTOM of R.id.football_image margin dip(10),
                            START to START of R.id.football_body margin dip(10),
                            END to END of R.id.football_body margin dip(10)
                        )
                    }

                    txt_desc{
                        connect(
                            TOP to BOTTOM of R.id.football_name margin dip(25),
                            START to START of R.id.football_body margin dip(15)
                        )
                    }
                }
            }
        }

        footballDatas = footballData.getData()
        val position = intent.getIntExtra("position",0)

        info("Football League ID : ${footballDatas[position].id}")



        val txt_name = find<TextView>(R.id.football_name)
        txt_name.setText(footballDatas[position].name)

        val txt_desc = find<TextView>(R.id.football_desc)
        txt_desc.setText(footballDatas[position].description)

        val img_fb = find<ImageView>(R.id.football_image)
        img_fb.setImageResource(footballDatas[position].imageDrawable)



    }
}
