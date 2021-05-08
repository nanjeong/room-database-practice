package com.example.myapplicationtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtracker.db.TodayWeight
import java.text.SimpleDateFormat

class TodayWeightAdapter : RecyclerView.Adapter<TodayWeightAdapter.ViewHolder>() {
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.weight_item_view, parent, false)
                return ViewHolder(view)
            }
        }

        val date: TextView = itemView.findViewById(R.id.date)
        val weight: TextView = itemView.findViewById(R.id.weight)

        fun bind(
            item: TodayWeight
        ) {
            val formatDate = SimpleDateFormat("MM/dd")
            date.text = formatDate.format(item.date)
            weight.text = item.weight.toString()
        }
    }

    var data = listOf<TodayWeight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder { //RecyclerView가 ViewHolder가 필요할 때 호출
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}