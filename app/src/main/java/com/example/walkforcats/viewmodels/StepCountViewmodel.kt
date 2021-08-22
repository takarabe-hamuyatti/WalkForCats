package com.example.walkforcats.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.walkforcats.listener.StepListener
import com.example.walkforcats.utils.StepDetector
import kotlinx.coroutines.launch

class StepCountViewmodel(application: Application): AndroidViewModel(application), SensorEventListener , StepListener {
    var sensorManager: SensorManager? = null
    var simpleStepDetector: StepDetector? = null
    var aDayGoal: Float = 0f

    init {
        getpreference()
    }

    private var _percent = MutableLiveData<Float>()
    val percent: LiveData<Float>
        get() = _percent

    private var _count = MutableLiveData<Int>().apply {
        value = 0
    }
    val count: LiveData<Int>
        get() = _count

    fun plusCount() {
        _count.value = _count.value?.plus(1)
    }

    fun getPercent() {
        //まずパーセントを出します
        var percentFloat = _count.value?.toFloat()?.div(aDayGoal)?.times(100)
        //少数第二位以下を切り捨てます。
        var Truncate= percentFloat?.times(10)?.toInt()?.toFloat()?.div(10)
        _percent.value = Truncate!!
    }

    fun getSensorManager(sensor: SensorManager) {
        sensorManager = sensor
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)
        if (sensorManager == null) {
            val cont = getApplication<Application>().applicationContext
            Toast.makeText(cont, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager!!.registerListener(
                this,
                sensorManager!!.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(
                event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }

    override fun onAccuracyChanged(p0: android.hardware.Sensor?, p1: Int) {
    }

    override fun step(timeNs: Long) {
        viewModelScope.launch {
            plusCount()
            getPercent()
        }
    }

    fun getpreference() {
        val cont = getApplication<Application>().applicationContext
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cont)
        aDayGoal = sharedPreferences.getString("goal", "15000")?.toFloat()!!
    }

}