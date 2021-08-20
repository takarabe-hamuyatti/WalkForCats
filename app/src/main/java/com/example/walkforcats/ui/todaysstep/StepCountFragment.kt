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


class StepCountFragment : Fragment() , SensorEventListener, StepListener {

    private val viewModel: StepCountViewmodel by navGraphViewModels(R.id.nest)

    private var _binding: FragmentStepCountBinding? = null
    private val binding get() = _binding!!

    private var simpleStepDetector: StepDetector? = null
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

        //contextが必要だったのでui層に設置しました。
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        if (sensorManager == null) {
            Toast.makeText(context, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager!!.registerListener(
                this,
                sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }

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

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        viewModel.plusCount()
        viewModel.getParcent()
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


