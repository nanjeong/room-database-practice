package com.example.myapplicationtracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myapplicationtracker.db.TodayWeight
import java.text.SimpleDateFormat

@BindingAdapter("setDate")
fun TextView.setDate(item: TodayWeight?) {
    item?.let {
        val formatDate = SimpleDateFormat("MM/dd")
        text = formatDate.format(item.date)
    }
}

@BindingAdapter("setWeight")
fun TextView.setWeight(item: TodayWeight?) {
    item?.let {
        text = item.weight.toString()
    }
}
