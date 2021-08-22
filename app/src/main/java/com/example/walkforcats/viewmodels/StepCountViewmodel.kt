package com.example.walkforcats.viewmodels

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walkforcats.listener.StepListener
import com.example.walkforcats.utils.StepDetector

class StepCountViewmodel():ViewModel(), SensorEventListener , StepListener{
    var sensorManager: SensorManager? = null
    var simpleStepDetector: StepDetector? = null


    private var _percent = MutableLiveData<Float>()
    val percent : LiveData<Float>
        get() = _percent

    private var _count = MutableLiveData<Int>().apply {
        value = 0
    }
    val count :LiveData<Int>
         get() = _count

    fun plusCount(){
        _count.value = _count.value?.plus(1)
    }

    fun getParcent(){
        var tmp = _count.value?.toFloat()?.times(100)
        _percent.value = tmp?.div(1000)
    }

     fun getSensorManager(sensor:SensorManager){
             sensorManager = sensor
             simpleStepDetector = StepDetector()
             simpleStepDetector!!.registerListener(this)
             if (sensorManager == null) {
                 /*Toast.makeText(context, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()

                  */
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
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun onAccuracyChanged(p0: android.hardware.Sensor?, p1: Int) {
    }

    override fun step(timeNs: Long) {
        plusCount()
        getParcent()
    }


}