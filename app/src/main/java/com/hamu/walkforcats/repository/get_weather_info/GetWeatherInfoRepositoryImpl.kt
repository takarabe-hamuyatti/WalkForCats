package com.hamu.walkforcats.repository.get_weather_info

import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.entity.WeatherResponse
import kotlinx.coroutines.*
import retrofit2.Response
import timber.log.Timber

class GetWeatherInfoRepositoryImpl(
    private val WeatherInfoService :WeatherInfoService
):GetWeatherInfoRepository{
    override suspend fun getWeatherInfo(longitude:Double,latitude:Double): Response<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            WeatherInfoService.getWeatherInfo(longitude,latitude)
        }
    }
}