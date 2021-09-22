package com.hamu.walkforcats.api

import com.hamu.walkforcats.entity.WeatherResponse
import com.hamu.walkforcats.utils.UniqueId.Companion.OPEN_WEATHER_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherInfoService {
    @GET("https://api.openweathermap.org/data/2.5/onecall?/lat={lat}&lon={lon}&lang=ja&appid={$OPEN_WEATHER_API_KEY}")
    suspend fun getWeatherInfo(
        @Path ("lat") lat:Double,
        @Path ("lon") lon:Double
    ):Response<WeatherResponse>
}