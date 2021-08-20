package com.example.walkforcats.ui.todaysstep

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.walkforcats.R
import com.example.walkforcats.databinding.ActivityTodaysStepBinding
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class TodaysStepActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private var isWalking = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f


    private lateinit var binding: ActivityTodaysStepBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodaysStepBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadDate()
        resetSteps()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val circularProgressBar = binding.circularProgressBar
        /*circularProgressBar.apply {
            // Set Progress
            progress = 65f
            // or with animation
            setProgressWithAnimation(65f, 1000) // =1s

            // Set Progress Max
            progressMax = 200f

            // Set ProgressBar Color
            progressBarColor = Color.BLACK
            // or with gradient
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.RED
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set background ProgressBar Color
            backgroundProgressBarColor = Color.GRAY
            // or with gradient
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.RED
            backgroundProgressBarColorDirection =
                CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set Width
            progressBarWidth = 7f // in DP
            backgroundProgressBarWidth = 3f // in DP

            // Other
            roundBorder = true
            startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

         */


    }

    override fun onResume() {
        super.onResume()
        isWalking = true
        val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        totalSteps++
        binding.stepcount.text = totalSteps.toString()

        binding.circularProgressBar.apply {
            setProgressWithAnimation(totalSteps.toFloat())
        }
        if(isWalking) {
            totalSteps = event!!.values[0]
            val currentSteps: Int = totalSteps.toInt()  - previousTotalSteps.toInt()
            binding.stepcount.text = currentSteps.toString()

            binding.circularProgressBar.apply {
                setProgressWithAnimation(200f)
            }

    }

     }
    fun resetSteps(){
        binding.stepcount.setOnClickListener {
            Toast.makeText(this,"長押しでリセットします", Toast.LENGTH_SHORT).show()
        }

        binding.stepcount.setOnLongClickListener {
            previousTotalSteps = totalSteps
            binding.stepcount.text = 0.toString()
            saveData()
            true
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun saveData(){
        val sharedPreferences:SharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putFloat("key1",previousTotalSteps)
        editor.apply()

    }

    private fun loadDate(){
        val sharedPreferences:SharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val savednumber:Float = sharedPreferences.getFloat("key1",0f)
        previousTotalSteps =  savednumber

    }

}