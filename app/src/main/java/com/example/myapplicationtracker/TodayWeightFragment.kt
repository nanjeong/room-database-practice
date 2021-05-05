package com.example.myapplicationtracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationtracker.databinding.FragmentTodayWeightTrackerBinding

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

        binding.setLifecycleOwner(this)

        binding.enter.setOnClickListener {
            todayWeightViewModel.afterInput(binding.editText.text.toString())
            binding.editText.setText("")
        }

        return binding.root
    }
}