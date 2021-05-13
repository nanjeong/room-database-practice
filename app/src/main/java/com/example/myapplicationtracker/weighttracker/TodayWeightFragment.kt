package com.example.myapplicationtracker.weighttracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplicationtracker.R
import com.example.myapplicationtracker.TodayWeightAdapter
import com.example.myapplicationtracker.TodayWeightListener
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

        val adapter = TodayWeightAdapter(TodayWeightListener { weightId ->
            todayWeightViewModel.onSomedayWeightClicked(weightId)
        })
        binding.weightList.adapter = adapter

        todayWeightViewModel.weights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        binding.enter.setOnClickListener{
            todayWeightViewModel.afterInput()

            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
            it.clearFocus()
        }

        todayWeightViewModel.navigationToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(TodayWeightFragmentDirections.actionTodayWeightFragmentToDetailFragment(it))
                todayWeightViewModel.onSomedayWeightNavigated()
            }
        })

        return binding.root
    }
}