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

    var inputWeight = MutableLiveData<String>()
    private val todayWeight = MutableLiveData<TodayWeight>()

    init {
        initializeTodayWeight()
    }

    private fun initializeTodayWeight() {
        viewModelScope.launch {
            todayWeight.value = getTodayWeight()
        }
    }

    private suspend fun getTodayWeight(): TodayWeight? {
        return database.getToday()
    }

    fun afterInput() {
        val newWeight = TodayWeight(weight = inputWeight.value?.toFloat())
        val newWeightDate = formatDate.format(newWeight.date)
        val todayWeightDate =
            if (todayWeight.value != null) formatDate.format(todayWeight.value?.date) else ""

        viewModelScope.launch {
            if (inputWeight.value?.isNotEmpty() == true) {
                if (newWeightDate == todayWeightDate) {
                    val oldWeight = todayWeight.value ?: return@launch
                    oldWeight?.weight = inputWeight.value?.toFloat()
                    update(oldWeight)
                } else {
                    insert(newWeight)
                }
                inputWeight.value = ""
                todayWeight.value = getTodayWeight()
            }
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            inputWeight.value = ""
            todayWeight.value = null
        }
    }

    private suspend fun insert(weight: TodayWeight) {
        database.insert(weight)
    }

    private suspend fun update(weight: TodayWeight) {
        database.update(weight)
    }

    private suspend fun clear() {
        database.clear()
    }

    val weights = database.getAllWeight()
    val weightString: LiveData<String> = Transformations.map(weights) { weight ->
        formatWeight(weight)
    }

    private val formatDate = SimpleDateFormat("yyyy/MM/dd")

    @SuppressLint("SimpleDateFormat")
    private fun formatWeight(weight: List<TodayWeight>): String {

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