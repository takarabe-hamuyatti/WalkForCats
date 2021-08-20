package com.example.walkforcats.ui.Chart

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walkforcats.databinding.FragmentDashboardBinding
import com.example.walkforcats.utils.StepDetector
import com.example.walkforcats.viewmodels.ChartViewModel

class ChartFragment : Fragment() {


    private lateinit var chartViewModel: ChartViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chartViewModel =
            ViewModelProvider(this).get(ChartViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        chartViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}