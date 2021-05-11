package com.example.myapplicationtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtracker.databinding.WeightItemViewBinding
import com.example.myapplicationtracker.db.TodayWeight
import java.text.SimpleDateFormat

class TodayWeightAdapter : ListAdapter<TodayWeight, TodayWeightAdapter.ViewHolder>(TodayWeightDiffCallback()) {

    class ViewHolder private constructor(val binding: WeightItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodayWeight) {
            binding.weightItem = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeightItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder { //RecyclerView가 ViewHolder가 필요할 때 호출
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class TodayWeightDiffCallback: DiffUtil.ItemCallback<TodayWeight>() {
    override fun areItemsTheSame(oldItem: TodayWeight, newItem: TodayWeight): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodayWeight, newItem: TodayWeight): Boolean {
        return oldItem == newItem
    }

}