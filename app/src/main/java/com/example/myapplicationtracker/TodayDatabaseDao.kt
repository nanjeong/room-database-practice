package com.example.myapplicationtracker

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodayDatabaseDao {
    @Insert
    suspend fun insert(today: TodayWeight)

    @Update
    suspend fun update(today: TodayWeight)

    @Query("SELECT * FROM today_data_table ORDER BY id")
    fun getAllWeight(): LiveData<List<TodayWeight>>

    @Query("SELECT * FROM today_data_table ORDER BY id DESC LIMIT 1")
    suspend fun getToday(): TodayWeight?
}