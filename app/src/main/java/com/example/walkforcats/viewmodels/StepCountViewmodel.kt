package com.example.walkforcats.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.walkforcats.listener.StepListener
import com.example.walkforcats.utils.StepDetector

class StepCountViewmodel(application: Application): AndroidViewModel(application), SensorEventListener , StepListener {
    var sensorManager: SensorManager? = null
    var simpleStepDetector: StepDetector? = null

    //設定画面や猫部屋から戻るたびに前回分がロードされるのを防ぐための判定に用います。
    // stepCountFragment が最初に作られ歩数をロードした時にfalse　に変え、 navgraph viewmodel（StepCountFragment） が　clearされる時にtureに戻ります。
    var isFirstinit = true

    //歩数の1日ごと、週間での集計です
    private var _count = MutableLiveData<Int>().apply {
        value = 0
    }
    val count: LiveData<Int>
        get() = _count

    private var _weeklyCount = MutableLiveData<Int>().apply {
        value = 0
    }
    val weeklyCount: LiveData<Int>
        get() = _weeklyCount


    //1日、１週間の目標です。
    private var _weeklyGoal = MutableLiveData<Float>().apply {
        value = 0f
    }
    val weeklyGoal: LiveData<Float>
        get() = _weeklyGoal

    private var _aDayGoal = MutableLiveData<Float>().apply {
        value = 0f
    }
     val aDayGoal: LiveData<Float>
        get() = _aDayGoal


    //1日単位、週間単位での歩数の達成率です
    private var _aDayPercent = MutableLiveData<Float>()
    val aDayPercent: LiveData<Float>
        get() = _aDayPercent

    private var _weeklyPercent = MutableLiveData<Float>()
    val weeklyPercent: LiveData<Float>
        get() = _weeklyPercent


    fun plusCount() {
        _count.value = _count.value?.plus(1)
        _weeklyCount.value = _weeklyCount.value?.plus(1)
    }

    fun getPercent() {
        //まずパーセントを出します
        var percentFloatofDay = count.value?.toFloat()?.div(aDayGoal.value!!)?.times(100)
        var percentFloatofWeek = weeklyCount.value?.toFloat()?.div(weeklyGoal.value!!)?.times(100)
        //少数第二位以下を切り捨てます。
        val TruncateofDay= percentFloatofDay?.times(10)?.toInt()?.toFloat()?.div(10)
        val TruncateofWeek= percentFloatofWeek?.times(10)?.toInt()?.toFloat()?.div(10)
        _aDayPercent.value = TruncateofDay!!
        _weeklyPercent.value = TruncateofWeek!!
    }


    //センサーマネージャー取得
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
                sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }


    //歩数検知
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

    //大元のオープンソースで用意されている関数
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    //歩数検知時の行動
    override fun step(timeNs: Long) {
        plusCount()
        getPercent()
    }


    //共有プリファレンス
    //その日ごとの記録は共有プリファレンスで行い、累計の記録はroom で行います。
    fun getGoalFromPreference() {
        val cont = getApplication<Application>().applicationContext
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cont)
        _aDayGoal.value = sharedPreferences.getString("aDayGoal", "15000")?.toFloat()
        _weeklyGoal.value = sharedPreferences.getString("weeklyGoal", "50000")?.toFloat()
    }

    //一日ごと、一週間ごとの目標を取得しています。
    fun getCountFromPreference(){
        val cont = getApplication<Application>().applicationContext
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cont)
        _count.value = sharedPreferences.getInt("todayCount", 0)
        _weeklyCount.value = sharedPreferences.getInt("weeklyCount", 0)
    }


    override fun onCleared() {
        super.onCleared()
        val cont = getApplication<Application>().applicationContext
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cont)

        sharedPreferences.edit {
            putInt("todayCount", _count.value?.toInt()!!)
            putInt("weeklyCount",_weeklyCount.value?.toInt()!!)
                .commit()
        }
        isFirstinit = !isFirstinit
    }
}