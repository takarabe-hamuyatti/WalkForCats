package com.hamu.walkforcats.ui.step_count

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentStepCountBinding
import com.hamu.walkforcats.viewmodels.ApplicationViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepCountFragment : Fragment(R.layout.fragment_step_count){

    private val viewModel: ApplicationViewmodel by activityViewModels()
    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.initWhenRedisplay()
        if(viewModel.isFirstInit){
            viewModel.getNowCount()
            viewModel.isFirstInit = !viewModel.isFirstInit
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentStepCountBinding.bind(view).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        viewModel.initWhenRedisplay()
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
                findNavController().navigate(R.id.action_navigation_step_to_navigation_settings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}


