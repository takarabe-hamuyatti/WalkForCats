package com.hamu.walkforcats.entity

data class WeathearInfo (
    val tmp :Int
)

data class WeatherResponse(val weathers:List<WeathearInfo>)