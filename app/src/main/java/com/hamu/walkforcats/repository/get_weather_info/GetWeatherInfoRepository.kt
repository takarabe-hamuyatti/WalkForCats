package com.hamu.walkforcats.repository.get_weather_info

import com.hamu.walkforcats.entity.WeatherResponse

interface GetWeatherInfoRepository {
    suspend fun getWeatherInfo(longitude:Double,latitude:Double):WeatherResponse
}