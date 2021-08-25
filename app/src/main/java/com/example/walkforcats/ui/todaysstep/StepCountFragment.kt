package com.example.walkforcats.ui.todaysstep

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.walkforcats.R
import com.example.walkforcats.databinding.FragmentStepCountBinding
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
            viewModel.getGoalFromPreference()
            viewModel.isFirstinit = !viewModel.isFirstinit
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStepCountBinding.inflate(inflater, container, false)

        binding.aDayCircularProgressBar.apply {
            //todo よしなに
        }

        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //センサー取得をして、実際の歩行検知をvieewmodelに任せています。
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        viewModel.getSensorManager(sensorManager!!)

        //viewmodelで処理した歩数、目標、達成率を画面に反映させています。
        viewModel.aDayPercent.observe(viewLifecycleOwner, {
            binding.aDayPercent.text  = "$it%"
        })

        viewModel.weeklyPercent.observe(viewLifecycleOwner, {
            binding.aWeeklyPercent.text = "$it%"
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


