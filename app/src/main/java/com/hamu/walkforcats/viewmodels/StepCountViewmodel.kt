package com.hamu.walkforcats.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.hamu.walkforcats.listener.StepListener
import com.hamu.walkforcats.repository.preferenceRepository
import com.hamu.walkforcats.utils.StepDetector

class StepCountViewmodel(application: Application): AndroidViewModel(application), SensorEventListener , StepListener {
    var sensorManager: SensorManager? = null
    var simpleStepDetector: StepDetector? = null


    @SuppressLint("StaticFieldLeak")
    val cont = getApplication<Application>().applicationContext
    val pref = PreferenceManager.getDefaultSharedPreferences(cont)

    val repository : preferenceRepository = preferenceRepository()

    //設定画面や猫部屋から戻るたびに前回分がロードされるのを防ぐための判定に用います。
    // stepCountFragment が最初に作られ歩数をロードした時にfalse　に変え、 navgraph viewmodel が　clearされる時にtureに戻ります。
    var isFirstinit = true
    //歩数の1日ごと、週間での集計です
    private val _dailyCount = MutableLiveData(0)
    val dailyCount: LiveData<String>
        get() = _dailyCount.map{it.toString()}

    private val _weeklyCount = MutableLiveData(0)
    val weeklyCount: LiveData<String>
        get() = _weeklyCount.map{it.toString()}


    //1日、１週間の目標です。
    private val _weeklyGoal = MutableLiveData(0)
    val weeklyGoal: LiveData<Int>
        get() = _weeklyGoal.map{it.toInt()}

    private val _dailyGoal = MutableLiveData(0)
     val dailyGoal: LiveData<Int>
        get() = _dailyGoal.map{it.toInt()}


    //1日単位、週間単位での歩数の達成率です
    private val _dailyPercent = MutableLiveData<Float>()
    val dailyPercent: LiveData<String>
        get() = _dailyPercent.map{"$it%"}

    private val _weeklyPercent = MutableLiveData<Float>()
    val weeklyPercent: LiveData<String>
        get() = _weeklyPercent.map {"$it%"}


    //センサーマネージャー取得
    fun getSensorManager(sensor: SensorManager) {
        sensorManager = sensor
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)
        if (sensorManager == null) {
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
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector?.updateAccelerometer(
                event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }

    //大元のオープンソースで用意されている関数
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // do nothing
    }

    //歩数検知時の行動
    override fun step(timeNs: Long) {
        plusCount()
        getPercent()
    }

    private fun plusCount() {
        _dailyCount.value = _dailyCount.value?.plus(1)
        _weeklyCount.value = _weeklyCount.value?.plus(1)
    }

    private fun getPercent() {
        //まずパーセントを出します
        val percentFloatofDay = getRatio(_dailyCount.value,_dailyGoal.value)
        val percentFloatofWeek = getRatio(_weeklyCount.value,_weeklyGoal.value)
        //少数第二位以下を切り捨てます。
        _dailyPercent.value = truncating(percentFloatofDay)
        _weeklyPercent.value = truncating(percentFloatofWeek)
    }

    //共有プリファレンス
    //その日ごとの記録は共有プリファレンスで行い、累計の記録はroom で行います。

    fun getCountFromPreference(){
        _dailyCount.value  = repository.getDailyCountFromPreference(pref)
        _weeklyCount.value  = repository.getWeeklyCountFromPreference(pref)
    }

    //一日ごと、一週間ごとの目標を取得しています。
    fun getGoalFromPreference() {
        _dailyGoal.value  = repository.getDailyGoalFromPreference(pref)
        _weeklyGoal.value  = repository.getWeeklyGoalFromPreference(pref)
    }

    override fun onCleared() {
        super.onCleared()
        repository.saveCount(pref,_dailyCount.value,_weeklyCount.value)
        isFirstinit = !isFirstinit
    }

    private fun getRatio(num1: Int?, num2: Int?): Float? {
        return num1?.toFloat()?.div(num2!!)?.times(100)
    }

    private fun truncating(num:Float?):Float?{
        return num?.times(10)?.toInt()?.toFloat()?.div(10)
    }

}