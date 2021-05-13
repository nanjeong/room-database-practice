package com.example.myapplicationtracker.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationtracker.R
import com.example.myapplicationtracker.databinding.FragmentDetailBinding
import com.example.myapplicationtracker.db.TodayDatabase

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireNotNull(this.activity).application

        val arguments = DetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = TodayDatabase.getInstance(application).todayDatabaseDao

        val viewModelFactory = DetailViewModelFactory(arguments.id, dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }


}