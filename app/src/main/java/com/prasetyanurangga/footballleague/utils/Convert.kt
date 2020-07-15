package com.prasetyanurangga.footballleague.utils

import java.text.SimpleDateFormat
import java.util.*

class Convert {
    companion object{
        fun convertDateLocalGTM(time: String?, date: String?): String{
            if(time.isNullOrEmpty() && !date.isNullOrEmpty())
            {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                val myDate: Date = simpleDateFormat.parse(date)
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                return formatter.format(myDate)
            }
            else if(time.isNullOrEmpty() && date.isNullOrEmpty())
            {
                return ""
            }
            else{
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                val myDate: Date = simpleDateFormat.parse(date+" "+time)
                val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss z", Locale.getDefault())
                return formatter.format(myDate)
            }

        }
    }
}