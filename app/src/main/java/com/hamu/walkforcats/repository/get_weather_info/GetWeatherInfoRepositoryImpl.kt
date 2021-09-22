package com.hamu.walkforcats.repository.get_weather_info

import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.entity.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherInfoRepositoryImpl(
    private val WeatherInfoService :WeatherInfoService
):GetWeatherInfoRepository{
    override suspend fun getWeatherInfo(longitude:Double,latitude:Double): WeatherResponse {
        lateinit var weathers:WeatherResponse
        val response = withContext(Dispatchers.IO) {WeatherInfoService.getWeatherInfo(longitude,latitude)}
        if(response.isSuccessful){
            weathers = response.body()!!
        }
        return weathers
    }

}