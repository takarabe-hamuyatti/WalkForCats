package com.hamu.walkforcats.viewmodels

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hamu.walkforcats.utils.sensor.StepListener
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.utils.sensor.StepDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StepCountViewmodel @Inject constructor(
    private val context : Application,
    private val preferenceRepository: PreferenceRepository
    ): AndroidViewModel(context), SensorEventListener , StepListener {
    private var sensorManager: SensorManager? = null
    private var simpleStepDetector: StepDetector? = null

    //設定画面や猫部屋から戻るたびに前回分がロードされるのを防ぐための判定に用います。
    // stepCountFragment が最初に作られ歩数をロードした時にfalse　に変え、activity viewmodel が　clearされる時にtureに戻ります。
    var isFirstinit = true

    //歩数の1日ごと、月ごとの集計です
    private val _dailyCount = MutableLiveData(0)
    val dailyCount: LiveData<String>
        get() = _dailyCount.map{it.toString()}

    private val _monthlyCount = MutableLiveData(0)
    val monthlyCount: LiveData<String>
        get() = _monthlyCount.map{it.toString()}


    //1日、１週間の目標です。
    private val _monthlyGoal = MutableLiveData(0)
    val monthlyGoal: LiveData<Float>
        get() = _monthlyGoal.map{it.toFloat()}

    private val _dailyGoal = MutableLiveData(0)
     val dailyGoal: LiveData<Float>
        get() = _dailyGoal.map{it.toFloat()}

    //1日単位、週間単位での歩数の達成率です
    private val _dailyPercent = MutableLiveData(0f)
    val dailyPercent: LiveData<String>
        get() = _dailyPercent.map{"$it%"}

    private val _monthlyPercent = MutableLiveData(0f)
    val monthlyPercent: LiveData<String>
        get() = _monthlyPercent.map {"$it%"}

    private val _isChangeCat = MutableLiveData(false)
    val isChangeCat:LiveData<Boolean>
        get() = _isChangeCat

    //画面を表示、再表示した時に獲得したい値をまとめています。歩数の読み込みはアプリ起動時にのみ読み込むので省いています。
    fun initWhenRedisplay(){
        getPercent()
        getGoal()
        checkChangeCat()
    }

    //歩数検知時の行動
    override fun step(timeNs: Long) {
        plusCount()
        getPercent()
    }

    private fun plusCount() {
        _dailyCount.value = _dailyCount.value?.plus(1)
        _monthlyCount.value = _monthlyCount.value?.plus(1)
    }

    fun getPercent() {
        //まずパーセントを出します
        val percentFloatofDay = getRatio(_dailyCount.value,_dailyGoal.value)
        val percentFloatofWeek = getRatio(_monthlyCount.value,_monthlyGoal.value)
        //少数第二位以下を切り捨てます。
        _dailyPercent.value = truncating(percentFloatofDay)
        _monthlyPercent.value = truncating(percentFloatofWeek)
    }

    //共有プリファレンス
    //その日ごとの記録は共有プリファレンスで行い、累計の記録はroom で行います。
    fun getNowCount(){
        _dailyCount.value  = preferenceRepository.getDailyCount()
        _monthlyCount.value  = preferenceRepository.getMonthlyCount()
    }

    //一日ごと、一週間ごとの目標を取得しています。
    private fun getGoal() {
        _dailyGoal.value  = preferenceRepository.getDailyGoal()
        _monthlyGoal.value  = preferenceRepository.getMonthlyGoal()
    }

    private fun checkChangeCat(){
        _isChangeCat.value = preferenceRepository.isCangeCat()
    }

    private fun resetViewmodelCount(){
        _dailyCount.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("save")
        preferenceRepository.saveCount(_dailyCount.value,_monthlyCount.value)
    }

    //センサーマネージャー取得
    fun getSensorManager(sensor: SensorManager) {
        sensorManager = sensor
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)
        sensorManager?.registerListener(
            this,
            sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
            ?: Toast.makeText(context, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()
    }

    //歩行検知
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

    private fun getRatio(num1: Int?, num2: Int?): Float? {
        return num1?.toFloat()?.div(num2!!)?.times(100)
    }

    private fun truncating(num:Float?):Float?{
        return num?.times(10)?.toInt()?.toFloat()?.div(10)
    }
}