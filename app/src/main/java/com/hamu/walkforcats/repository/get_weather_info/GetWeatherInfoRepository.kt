package com.hamu.walkforcats.repository.get_weather_info

import com.hamu.walkforcats.entity.WeathearInfo

interface GetWeatherInfoRepository {
    suspend fun getWeatherInfo(longitude:Float,latitude:Float):WeathearInfo
}