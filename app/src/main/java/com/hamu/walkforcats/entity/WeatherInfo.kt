package com.hamu.walkforcats.entity

data class WeatherResponse(var list: List<WeatherInfo>? = null)

data class WeatherInfo(
    var base: String,
    var clouds: Clouds,
    var cod: Int,
    var dt: Int,
    var id: Int,
    var main: Main,
    var name: String,
    var visibility: Int,
    var weather: List<WeatherX>,
    var wind: Wind
)

data class Clouds(
    var all: Int
)

data class Main(
    var humidity: Int,
    var pressure: Int,
    var temp: Double,
    var temp_max: Int,
    var temp_min: Int
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
