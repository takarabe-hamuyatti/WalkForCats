package com.hamu.walkforcats.api

import com.hamu.walkforcats.entity.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherInfoService {
    @GET("https://api.openweathermap.org/data/2.5/onecall?/{lat}/{lon}")
    suspend fun getWeatherInfo(
        @Path ("lat") lat:Float,
        @Path ("lon") lon:Float
    ):Response<WeatherResponse>
}