package com.example.myapplicationtracker

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.myapplicationtracker.db.TodayDatabaseDao
import com.example.myapplicationtracker.db.TodayWeight
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TodayWeightViewModel(
    val database: TodayDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val inputWeight = MutableLiveData<String>()

    fun afterInput() {
        viewModelScope.launch {
            if (inputWeight.value?.isNotEmpty() == true) {
                val newWeight = TodayWeight(weight = inputWeight.value?.toFloat())
                insert(newWeight)
            }
        }
    }

    private suspend fun insert(weight: TodayWeight) {
        database.insert(weight)
    }

    private val weights = database.getAllWeight()
    val weightString: LiveData<String> = Transformations.map(weights) { weight ->
        formatWeight(weight)
    }

    @SuppressLint("SimpleDateFormat")
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