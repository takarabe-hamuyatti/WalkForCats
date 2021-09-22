package com.hamu.walkforcats.repository.get_weather_info

import android.location.Location
import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.entity.WeathearInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherInfoRepositoryImpl(
    private val WeatherInfoService :WeatherInfoService
):GetWeatherInfoRepository{
    override suspend fun getWeatherInfo(longitude:Float,latitude:Float): WeathearInfo {
       val tmp = withContext(Dispatchers.IO) {WeatherInfoService.getWeatherInfo(longitude,latitude)}
    }

}