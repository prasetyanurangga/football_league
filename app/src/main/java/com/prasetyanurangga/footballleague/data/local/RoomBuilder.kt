package com.prasetyanurangga.footballleague.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prasetyanurangga.footballleague.data.dao.LocalDao
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.TeamModel

@Database(
    entities = arrayOf(EventModel::class, TeamModel::class),
    version = 3
)
abstract class RoomBuilder : RoomDatabase() {

    abstract fun localDao(): LocalDao
    companion object {
        private var INSTANCE: RoomBuilder? = null

        fun getInstance(context: Context): RoomBuilder? {
            if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RoomBuilder::class.java, "FootballData.db")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}