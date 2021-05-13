package com.example.myapplicationtracker.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationtracker.db.TodayDatabaseDao
import com.example.myapplicationtracker.db.TodayWeight

class DetailViewModel(
    private val weightKey: Long = 0L,
    val database: TodayDatabaseDao,
) : ViewModel() {
    val cheeringPhrase = mutableListOf(
        "You can do it",
        "Go for it",
        "You can make it",
        "Stick to it",
        "Keep it up",
        "Keep working\n on it",
        "Don't give up",
        "You've made it\n this far",
    )

    val phrase = getCheeringPhrase()

    private fun getCheeringPhrase(): String {
        cheeringPhrase.shuffle()
        return cheeringPhrase[0]
    }

    private val weight: LiveData<TodayWeight>
    fun getWeight() = weight

    init {
        weight = database.getWeightWithId(weightKey)
    }
}