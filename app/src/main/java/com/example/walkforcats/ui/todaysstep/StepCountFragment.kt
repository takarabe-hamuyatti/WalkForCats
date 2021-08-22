package com.example.walkforcats.ui.todaysstep

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.walkforcats.R
import com.example.walkforcats.databinding.FragmentStepCountBinding
import com.example.walkforcats.listener.StepListener
import com.example.walkforcats.utils.StepDetector
import com.example.walkforcats.viewmodels.StepCountViewmodel


class StepCountFragment : Fragment() /*, SensorEventListener, StepListener */{

    private val viewModel: StepCountViewmodel by navGraphViewModels(R.id.nest)

    private var _binding: FragmentStepCountBinding? = null
    private val binding get() = _binding!!

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStepCountBinding.inflate(inflater, container, false)

        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        viewModel.getSensorManager(sensorManager!!)


        binding.goCatRoom.setOnClickListener{
            findNavController().navigate(R.id.action_stepCountFragment_to_catRoomFragment)
        }

        viewModel.percent.observe(viewLifecycleOwner, {
            binding.percent.text  = "$it%"
        })

        viewModel.count.observe(viewLifecycleOwner,{
            binding.count.text = it.toString()
            binding.circularProgressBar.apply {
                setProgressWithAnimation(it.toFloat())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(R.id.action_stepCountFragment_to_settingsFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}


