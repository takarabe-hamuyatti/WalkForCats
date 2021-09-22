package com.hamu.walkforcats.repository.get_weather_info

import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.entity.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GetWeatherInfoRepositoryImpl(
    private val WeatherInfoService :WeatherInfoService
):GetWeatherInfoRepository{
    override suspend fun getWeatherInfo(longitude:Double,latitude:Double): WeatherResponse {
        lateinit var weathers:WeatherResponse
        val response = withContext(Dispatchers.IO) {
            WeatherInfoService.getWeatherInfo(longitude,latitude)
        }
        if(response.isSuccessful){
            weathers = response.body()!!
            val tmp = weathers.list[0].clouds
            Timber.i("$tmp")
        }
        return response.body()!!
    }
}