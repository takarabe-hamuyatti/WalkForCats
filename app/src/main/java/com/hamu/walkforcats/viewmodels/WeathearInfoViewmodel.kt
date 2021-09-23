package com.hamu.walkforcats.viewmodels

import androidx.lifecycle.*
import com.hamu.walkforcats.entity.PastLocation
import com.hamu.walkforcats.entity.WeatherResponse
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepository
import com.hamu.walkforcats.repository.past_location.PastLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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

    //過去の位置情報が使えたらそのまま使って、ダメだったらダイアログで知らせます。
    fun checkIsPastLocationIsNull(){
        if(pastLocationinfo.value==null){
            displayDialog()
        }else{
            pastLocationinfo.value?.let{
                val longitude =it.longitude
                val latitude = it.longitude
                getWeatherInfo(longitude,latitude)
            }
        }
    }

    fun getWeatherInfo(longitude:Double,latitude:Double) {
        viewModelScope.launch {
            val response = weatherRepository.getWeatherInfo(longitude, latitude)
            if(response.isSuccessful){
                _weatherList.value = response.body()
                val tmp = response.body()?.list?.get(0)?.main?.temp
                Timber.i("$tmp")
                Timber.i("Sucsess")
            }
            else {
                displayDialog()
                Timber.i("notSucsess")
            }
        }
    }

    fun updatePastLocation(longitude:Double,latitude:Double){
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