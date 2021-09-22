package com.hamu.walkforcats.viewmodels

import android.location.Location
import androidx.lifecycle.*
import com.hamu.walkforcats.entity.PastLocation
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepository
import com.hamu.walkforcats.repository.past_location.PastLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeathearInfoViewmodel @Inject constructor(
    private val weatherRepository: GetWeatherInfoRepository,
    pastLocationRepository: PastLocationRepository
):ViewModel() {
    val pastLocationinfo:LiveData<PastLocation> = pastLocationRepository.PastLocationInfo.asLiveData()

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
    private fun getWeatherInfo(longitude:Float,latitude:Float) {
        viewModelScope.launch {
            weatherRepository.getWeatherInfo(longitude, latitude)
        }
    }
    private fun displayDialog(){
        _isDisplayDaialog.value = true
    }
    fun hideDialog(){
        _isDisplayDaialog.value = false
    }
}