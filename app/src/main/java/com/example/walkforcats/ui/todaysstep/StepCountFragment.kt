package com.example.walkforcats.ui.todaysstep

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.IBinder
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.preference.PreferenceManager
import com.example.walkforcats.R
import com.example.walkforcats.databinding.FragmentStepCountBinding
import com.example.walkforcats.listener.StepListener
import com.example.walkforcats.utils.StepDetector
import com.example.walkforcats.viewmodels.StepCountViewmodel


class StepCountFragment : Fragment(){

    private val viewModel: StepCountViewmodel by navGraphViewModels(R.id.nest)
    private var count = 0
    private var weeklyCount = 0

    private var _binding: FragmentStepCountBinding? = null
    private val binding get() = _binding!!

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        //設定画面や猫部屋から戻るたびに前回終了時のカウントがロードされるのを防ぐためにif文を設けています
        if(viewModel.isFirstinit) {
            viewModel.getCountFromPreference()
            viewModel.getpreference()
            viewModel.isFirstinit = !viewModel.isFirstinit
        }

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

        viewModel.aDayPercent.observe(viewLifecycleOwner, {
            binding.aDayPercent.text  = "$it%"
        })

        viewModel.weeklyPercent.observe(viewLifecycleOwner, {
            binding.aWeeklyPercent.text  = "$it%"
        })

        viewModel.count.observe(viewLifecycleOwner,{
            count = it
            binding.count.text = it.toString()
            binding.aDayCircularProgressBar.apply {
                setProgressWithAnimation(it.toFloat())
            }
        })

        viewModel.weeklyCount.observe(viewLifecycleOwner,{
            weeklyCount = it
            binding.weeklyCount.text = it.toString()
            binding.weeklyCircularProgressBar.apply {
                setProgressWithAnimation(it.toFloat())
            }
        })

        viewModel.aDayGoal.observe(viewLifecycleOwner,{
            binding.aDayCircularProgressBar.apply {
                progressMax = it
            }
        })

        viewModel.weeklyGoal.observe(viewLifecycleOwner,{
            binding.weeklyCircularProgressBar.apply {
                progressMax = it
            }
        })



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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //saveData()
    }


}


