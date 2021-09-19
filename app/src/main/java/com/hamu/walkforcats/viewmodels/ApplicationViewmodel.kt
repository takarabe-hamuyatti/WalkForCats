package com.hamu.walkforcats.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.lifecycle.*
import com.hamu.walkforcats.R
import com.hamu.walkforcats.utils.sensor.StepListener
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.utils.getRatio
import com.hamu.walkforcats.utils.sensor.StepDetector
import com.hamu.walkforcats.utils.truncating
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ApplicationViewmodel @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    sensorManager: SensorManager,
    context : Application
    ): AndroidViewModel(context), SensorEventListener , StepListener /*,DefaultLifecycleObserver */ {
    private var simpleStepDetector: StepDetector? = null

    var isFirstInit = true

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

    private val _whichCatToSet = MutableLiveData(0)
    val whichCatToSet:LiveData<Int>
         get() = _whichCatToSet

    var isChangeCat =false

    //画面を表示、再表示した時に獲得したい値をまとめています。歩数の読み込み(getNowCount)はアプリ起動時にのみ読み込むので省いています。
    fun initWhenRedisplay(){
        getPercent()
        getGoal()
        checkChangeCat()
        getRangeOfPercent()
    }

    //歩行検知時の行動
    override fun step(timeNs: Long) {
        plusCount()
        getPercent()
        getRangeOfPercent()
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
        _monthlyCount.value  = preferenceRepository.getMonthlyCount().toInt()
    }

    //一日ごと、一週間ごとの目標を取得しています。
    private fun getGoal() {
        _dailyGoal.value  = preferenceRepository.getDailyGoal()
        _monthlyGoal.value  = preferenceRepository.getMonthlyGoal().toInt()
    }

    private fun checkChangeCat(){
        isChangeCat= preferenceRepository.isCangeCat()
    }

    private fun getRangeOfPercent(){
        _dailyPercent.value?.let {
            if(isChangeCat) {
                if (10 >= it) { _whichCatToSet.value = R.drawable.realcat1 }
                else if (30 >= it) { _whichCatToSet.value = R.drawable.realcat2 }
                else if (60 >= it) {_whichCatToSet.value = R.drawable.realcat3 }
                else if (80 >= it) { _whichCatToSet.value= R.drawable.realcat4 }
                else { _whichCatToSet.value = R.drawable.realcat5 }
            }else{
                if (10 >= it) { _whichCatToSet.value = R.drawable.whitecat1 }
                else if (30 >= it) { _whichCatToSet.value = R.drawable.whitecat2 }
                else if (60 >= it) { _whichCatToSet.value = R.drawable.whitecat3 }
                else if (80 >= it) { _whichCatToSet.value = R.drawable.whitecat4 }
                else { _whichCatToSet.value = R.drawable.whitecat5 }
            }
        }
    }

    //センサーマネージャー取得
    init {
        simpleStepDetector = StepDetector()
        simpleStepDetector?.let{
            it.registerListener(this)
            sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
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

    fun saveCount(){
        preferenceRepository.saveCount(_dailyCount.value,_monthlyCount.value)
        isFirstInit = !isFirstInit
    }


    override fun onCleared() {
        super.onCleared()
        saveCount()
    }
}