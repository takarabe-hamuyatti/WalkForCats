package com.hamu.walkforcats.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.entity.PastLocation
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepository
import com.hamu.walkforcats.repository.past_location.PastLocationRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeathearInfoViewmodel @Inject constructor(
    weatherRepository: GetWeatherInfoRepository,
    pastLocationRepository: PastLocationRepository
):ViewModel() {
    val pastLocationinfo:LiveData<PastLocation> = pastLocationRepository.PastLocationInfo.asLiveData()
}