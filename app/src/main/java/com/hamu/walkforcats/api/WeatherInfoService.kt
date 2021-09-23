package com.hamu.walkforcats.api

import com.hamu.walkforcats.entity.WeatherResponse
import com.hamu.walkforcats.utils.UniqueId.Companion.OPEN_WEATHER_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WeatherInfoService {
    @GET("onecall")
    suspend fun getWeatherInfo(
        @Query ("lat") lat:Double,
        @Query ("lon") lon:Double,
        @Query ("lang") lang:String = "ja",
        @Query ("appid") appid:String = OPEN_WEATHER_API_KEY//todo 隠し方探す
    ): Response<WeatherResponse>
}

