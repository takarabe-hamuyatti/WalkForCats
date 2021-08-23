package com.example.walkforcats.ui.title

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.walkforcats.R
import com.example.walkforcats.viewmodels.StepCountViewmodel


class initFragment : Fragment() {
    private val viewModel: StepCountViewmodel by navGraphViewModels(R.id.nest)

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        viewModel.getSensorManager(sensorManager!!)
        //stepCountFragment　の中に配置してしまうと、設定画面や猫部屋から戻るたびに前回保存分がリロードされてしまうため初期化用fragmentを作りました。
        viewModel.getpreference()
        viewModel.getCountFromPreference()

         */


        findNavController().navigate(R.id.action_initFragment_to_stepCountFragment)

        return view
    }
}