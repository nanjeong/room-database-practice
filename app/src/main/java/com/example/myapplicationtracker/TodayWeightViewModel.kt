package com.example.myapplicationtracker

import android.app.Application
import android.text.Spanned
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TodayWeightViewModel(
    val database: TodayDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var todayWeight = MutableLiveData<TodayWeight>()

    init {
        initializeTodayWeight()
    }

    private fun initializeTodayWeight() {
        viewModelScope.launch {
            todayWeight.value = getTodayWeightFromDatabase()
        }
    }

    private suspend fun getTodayWeightFromDatabase(): TodayWeight? {
        var weight = database.getToday()

        return weight
    }

    fun afterInput(weight: String) {
        viewModelScope.launch {
            if (weight.isNotEmpty()) {
                val newWeight = TodayWeight(weight = weight.toFloat())
                insert(newWeight)
                todayWeight.value = getTodayWeightFromDatabase()
            }
        }
    }

    private suspend fun insert(weight: TodayWeight) {
        database.insert(weight)
    }

    private val weights = database.getAllWeight()
    val weightString = Transformations.map(weights) { weight ->
        formatWeight(weight)
    }

    private fun formatWeight(weight: List<TodayWeight>): String {
        val formatDate = SimpleDateFormat("yyyy/MM/dd")
        val sb = StringBuilder()
        sb.apply {
            weight.forEach {
                append(formatDate.format(Date(it.date)))
                append(" ${it.weight}\n\n")
            }
        }
        return sb.toString()
    }

}