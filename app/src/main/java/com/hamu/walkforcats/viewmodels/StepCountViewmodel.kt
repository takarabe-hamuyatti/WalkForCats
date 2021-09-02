package com.hamu.walkforcats.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.CreateFinishedMonthRepository
import com.hamu.walkforcats.utils.StepListener
import com.hamu.walkforcats.repository.PreferenceRepository
import com.hamu.walkforcats.utils.StepDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StepCountViewmodel (
    application: Application
    ): AndroidViewModel(application), SensorEventListener , StepListener {
    var sensorManager: SensorManager? = null
    var simpleStepDetector: StepDetector? = null


    @SuppressLint("StaticFieldLeak")
    val cont = application
    val pref = PreferenceManager.getDefaultSharedPreferences(cont)

    val repository : PreferenceRepository = PreferenceRepository(pref)

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


    //センサーマネージャー取得
    fun getSensorManager(sensor: SensorManager) {
        sensorManager = sensor
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)
        if (sensorManager == null) {
            Toast.makeText(cont, "端末にセンサーが用意されていません。", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.let {
                it.registerListener(
                    this,
                    sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            }
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
        _monthlyCount.value = _monthlyCount.value?.plus(1)
    }

    private fun getPercent() {
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
        _dailyCount.value  = repository.getDailyCount()
        _monthlyCount.value  = repository.getMonthlyCount()
    }

    //一日ごと、一週間ごとの目標を取得しています。
    fun getGoal() {
        _dailyGoal.value  = repository.getDailyGoal()
        _monthlyGoal.value  = repository.getMonthlyGoal()
    }

    private fun getRatio(num1: Int?, num2: Int?): Float? {
        return num1?.toFloat()?.div(num2!!)?.times(100)
    }

    private fun truncating(num:Float?):Float?{
        return num?.times(10)?.toInt()?.toFloat()?.div(10)
    }

    override fun onCleared() {
        super.onCleared()
        repository.saveCount(_dailyCount.value,_monthlyCount.value)
        isFirstinit = !isFirstinit
    }

    fun room(){
        viewModelScope.launch(Dispatchers.IO) {
            val finishedMonthlyInfo =
                monthlyInfo(
                    yearMonth = 200007,
                    stepCount = 24,
                    goalOfMonth = 5000,
                    percentOfMonthlyGoal = 12.0f
                )

            val Dao = getDatabase(cont).aboutMonthlyInfoDao
            val createFinishedMonthRepository = CreateFinishedMonthRepository()
            createFinishedMonthRepository.createFinishedMonth(Dao,monthlyInfo = finishedMonthlyInfo)
        }
    }
}