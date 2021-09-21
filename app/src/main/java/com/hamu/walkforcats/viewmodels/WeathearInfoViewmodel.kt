package com.hamu.walkforcats.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeathearInfoViewmodel @Inject constructor(
    weatherRepository: GetWeatherInfoRepository
):ViewModel() {
    private val _location = MutableLiveData<Location>()
}