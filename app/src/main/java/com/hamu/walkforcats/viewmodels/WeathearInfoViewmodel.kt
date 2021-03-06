package com.hamu.walkforcats.viewmodels

import android.location.Location
import androidx.lifecycle.*
import com.hamu.walkforcats.entity.PastLocation
import com.hamu.walkforcats.entity.WeatherResponse
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepository
import com.hamu.walkforcats.repository.past_location.PastLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeathearInfoViewmodel @Inject constructor(
    private val weatherRepository: GetWeatherInfoRepository,
    private val pastLocationRepository: PastLocationRepository
):ViewModel() {
    //位置情報が取れなかった時用に過去の最新分の位置情報をストックしています。
    val pastLocationinfo:LiveData<PastLocation> = pastLocationRepository.PastLocationInfo.asLiveData()

    private val _weatherList = MutableLiveData<WeatherResponse>()
    val weatherList:LiveData<WeatherResponse>
          get() = _weatherList

    private var _isDisplayDialog = MutableLiveData(false)
    val isDisplayDialog:LiveData<Boolean>
          get() = _isDisplayDialog

    fun decideWorks(location:Location?){
        if(location !=null){
            val longitude = location.longitude
            val latitude = location.latitude
            getWeatherInfo(longitude,latitude)
            //位置情報を取得したら、過去の位置情報を更新します。
            updatePastLocation(longitude,latitude)
        }else{
            //位置情報の取得に失敗したら、過去の位置情報が利用できるか確認します。
            checkIsPastLocationIsNull()
        }
    }
    //現在の位置情報をえられない時、過去の位置情報が使えるか確認しています。
    fun checkIsPastLocationIsNull() {
        if (pastLocationinfo.value != null) {
            pastLocationinfo.value?.let {
                val longitude = it.longitude
                val latitude = it.longitude
                getWeatherInfoByPastLocation(longitude, latitude)
            }
        }else displayDialog()
    }

    private fun getWeatherInfo(longitude:Double,latitude:Double) {
        viewModelScope.launch {
            val response = weatherRepository.getWeatherInfo(longitude, latitude)
            if(response.isSuccessful){
                _weatherList.value = response.body()
            }
            else {
                displayDialog()
            }
        }
    }
    private fun getWeatherInfoByPastLocation(longitude:Double, latitude:Double) {
        viewModelScope.launch {
            val response = weatherRepository.getWeatherInfo(longitude, latitude)
            if(response.isSuccessful){
                _weatherList.value = response.body()
            }else displayDialog()
        }
    }
    private fun updatePastLocation(longitude:Double,latitude:Double){
        viewModelScope.launch {
            pastLocationRepository.updatePastLocation(longitude,latitude)
        }
    }
    private fun displayDialog(){
        _isDisplayDialog.value = true
    }
    fun hideDialog(){
        _isDisplayDialog.value = false
    }
}