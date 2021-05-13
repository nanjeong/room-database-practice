package com.example.myapplicationtracker.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationtracker.db.TodayDatabaseDao
import java.lang.IllegalArgumentException

class DetailViewModelFactory(
    private val weightKey: Long,
    private val dataSource: TodayDatabaseDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(weightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}