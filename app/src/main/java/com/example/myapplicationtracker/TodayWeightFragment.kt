package com.example.myapplicationtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationtracker.databinding.FragmentTodayWeightTrackerBinding
import com.example.myapplicationtracker.db.TodayDatabase

class TodayWeightFragment : Fragment() {

    private lateinit var binding : FragmentTodayWeightTrackerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_today_weight_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TodayDatabase.getInstance(application).todayDatabaseDao

        val viewModelFactory = TodayWeightViewModelFactory(dataSource, application)

        val todayWeightViewModel =
            ViewModelProvider(this, viewModelFactory).get(TodayWeightViewModel::class.java)

        binding.todayWeightViewModel = todayWeightViewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}