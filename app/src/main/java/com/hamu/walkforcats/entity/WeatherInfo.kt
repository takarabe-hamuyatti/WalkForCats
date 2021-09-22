package com.hamu.walkforcats.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(val list: List<WeatherInfo>)

@JsonClass(generateAdapter = true)
data class WeatherInfo(
    var base: String,
    var clouds: Clouds,
    var cod: Int,
    var coord: Coord,
    var dt: Int,
    var id: Int,
    var main: Main,
    var name: String,
    var sys: Sys,
    var visibility: Int,
    var weather: List<WeatherX>,
    var wind: Wind
)

data class Clouds(
    var all: Int
)

data class Coord(
    var lat: Double,
    var lon: Double
)

data class Main(
    var humidity: Int,
    var pressure: Int,
    var temp: Double,
    var temp_max: Int,
    var temp_min: Int
)

data class Sys(
    var country: String,
    var id: Int,
    var message: Double,
    var sunrise: Int,
    var sunset: Int,
    var type: Int
)

data class WeatherX(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
)

data class Wind(
    var deg: Int,
    var speed: Double
)
