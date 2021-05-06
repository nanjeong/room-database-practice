package com.example.myapplicationtracker.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodayDatabaseDao {
    @Insert
    suspend fun insert(today: TodayWeight)

    @Update
    suspend fun update(today: TodayWeight)

    @Query("SELECT * FROM today_data_table ORDER BY id DESC")
    fun getAllWeight(): LiveData<List<TodayWeight>>

    @Query("SELECT * FROM today_data_table ORDER BY id DESC LIMIT 1")
    suspend fun getToday(): TodayWeight?

    @Query("DELETE FROM today_data_table")
    suspend fun clear()
}