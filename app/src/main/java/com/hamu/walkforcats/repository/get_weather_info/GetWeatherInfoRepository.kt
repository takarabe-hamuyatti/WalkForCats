package com.hamu.walkforcats.repository.get_weather_info

import com.hamu.walkforcats.entity.WeatherResponse
import retrofit2.Response

interface GetWeatherInfoRepository {
    suspend fun getWeatherInfo(longitude:Double,latitude:Double): Response<WeatherResponse>
}