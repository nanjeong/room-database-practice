package com.example.myapplicationtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodayWeight::class], version = 1, exportSchema = false)
abstract class TodayDatabase : RoomDatabase() {

    abstract val todayDatabaseDao: TodayDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TodayDatabase? = null

        fun getInstance(context: Context): TodayDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodayDatabase::class.java,
                        "weight_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}