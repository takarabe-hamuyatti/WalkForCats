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
    val pastLocationinfo:LiveData<PastLocation> = pastLocationRepository.PastLocationInfo.asLiveData()

    private val _weatherList = MutableLiveData<WeatherResponse>()
    val weatherList:LiveData<WeatherResponse>
          get() = _weatherList

    private var _isDisplayDaialog = MutableLiveData(false)
    val isDisplayDaialog:LiveData<Boolean>
          get() = _isDisplayDaialog

    fun checkIsPastLocationIsNull(){
        if(pastLocationinfo.value==null){
            displayDialog()
        }else{
            val longitude = pastLocationinfo.value!!.longitude
            val latitude = pastLocationinfo.value!!.longitude
            getWeatherInfo(longitude,latitude)
        }
    }

    fun getWeatherInfo(longitude:Double,latitude:Double) {
        viewModelScope.launch {
            _weatherList.value = weatherRepository.getWeatherInfo(longitude, latitude)
        }
    }

    fun updatePastLocation(longitude:Double,latitude:Double){
        pastLocationRepository.updatePastLocation(longitude,latitude)
    }
    private fun displayDialog(){
        _isDisplayDaialog.value = true
    }
    fun hideDialog(){
        _isDisplayDaialog.value = false
    }
}