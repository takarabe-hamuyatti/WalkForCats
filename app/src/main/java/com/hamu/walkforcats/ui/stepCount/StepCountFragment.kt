package com.hamu.walkforcats.ui.stepCount

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentStepCountBinding
import com.hamu.walkforcats.viewmodels.StepCountViewmodel


class StepCountFragment : Fragment(){

    private val viewModel: StepCountViewmodel by navGraphViewModels(R.id.nest)

    private var _binding: FragmentStepCountBinding? = null
    private val binding get() = _binding!!

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        //設定画面や猫部屋から戻るたびに前回終了時のカウントがロードされるのを防ぐためにif文を設けています
        if(viewModel.isFirstinit) {
            viewModel.getCountFromPreference()
            viewModel.getGoalFromPreference()
            viewModel.isFirstinit = !viewModel.isFirstinit
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepCountBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.dailyCircularProgressBar.apply {
            //todo よしなに
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //センサー取得をして、実際の歩行検知をvieewmodelに任せています。
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.let { viewModel.getSensorManager(it)}

        viewModel.dailyCount.observe(viewLifecycleOwner,{
            binding.dailyCircularProgressBar.apply {
                setProgressWithAnimation(it.toFloat())
            }
        })

        viewModel.weeklyCount.observe(viewLifecycleOwner,{
            binding.weeklyCircularProgressBar.apply {
                setProgressWithAnimation(it.toFloat())
            }
        })

        binding.goCatRoom.setOnClickListener{
            findNavController().navigate(R.id.action_stepCountFragment_to_catRoomFragment)
        }

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
    }
}


