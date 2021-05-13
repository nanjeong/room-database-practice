package com.example.myapplicationtracker.weighttracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationtracker.db.TodayDatabaseDao
import java.lang.IllegalArgumentException

class TodayWeightViewModelFactory(
    private val dataSource: TodayDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodayWeightViewModel::class.java)) {
            return TodayWeightViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}