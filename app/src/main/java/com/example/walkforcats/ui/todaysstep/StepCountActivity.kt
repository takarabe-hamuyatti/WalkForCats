package com.example.walkforcats.ui.todaysstep

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.walkforcats.R
import com.example.walkforcats.databinding.ActivityStepCountBinding
import com.example.walkforcats.listener.StepListener
import com.example.walkforcats.ui.setting.SettingsFragment
import com.example.walkforcats.utils.StepDetector
import com.example.walkforcats.viewmodels.CatRoomViewModel
import com.example.walkforcats.viewmodels.StepCountViewmodel
import kotlin.properties.Delegates

class StepCountActivity : AppCompatActivity(), SensorEventListener, StepListener {
        private lateinit var viewmodel: StepCountViewmodel

        private lateinit var binding:ActivityStepCountBinding

        private var simpleStepDetector: StepDetector? = null
        private var sensorManager: SensorManager? = null
        private var count by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityStepCountBinding.inflate(layoutInflater)
            setContentView(binding.root)


            viewmodel =
                ViewModelProvider(this).get(StepCountViewmodel::class.java)

            //センサーについて、model 側で動かそうと思ったのですが、cntextが必要だったのでui層に設置しました。
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

            simpleStepDetector = StepDetector()
            simpleStepDetector!!.registerListener(this)

                if (sensorManager == null) {
                    Toast.makeText(this, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()
                } else {
                    sensorManager!!.registerListener(
                        this,
                        sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_FASTEST
                    )
            }

            viewmodel.percent.observe(this, {
              binding.percent.text  = "$it%"
            })

            viewmodel.count.observe(this,  {
                count = it
                binding.count.text = count.toString()
                binding.circularProgressBar.apply {
                    setProgressWithAnimation(count.toFloat())
                }
            })

            binding.goCatRoom.setOnClickListener {
            }



        }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        viewmodel.plusStep()
        viewmodel.getParcent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()


    }
}
