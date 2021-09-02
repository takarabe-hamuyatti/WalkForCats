package com.hamu.walkforcats.ui.stepCount

import android.content.Context
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hamu.walkforcats.R
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.databinding.FragmentStepCountBinding
import com.hamu.walkforcats.repository.CreateFinishedMonthRepository
import com.hamu.walkforcats.viewmodels.StepCountViewmodel


class StepCountFragment : Fragment(R.layout.fragment_step_count){

    private val viewModel: StepCountViewmodel by activityViewModels()
    private var sensorManager: SensorManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.room()//動作確認　後で消します。

        //設定画面や猫部屋から戻るたびに前回終了時のカウントがロードされるのを防ぐためにif文を設けています
        if(viewModel.isFirstinit) {
            viewModel.getNowCount()
            viewModel.getGoal()
            viewModel.isFirstinit = !viewModel.isFirstinit
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentStepCountBinding.bind(view).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        //センサー取得をして、実際の歩行検知をvieewmodelに任せています。
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.let { viewModel.getSensorManager(it)}

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
            R.id.action_history -> {
                findNavController().navigate(R.id.action_stepCountFragment_to_historyFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}


