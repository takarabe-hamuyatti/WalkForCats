package com.hamu.walkforcats.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

//data class WeatherResponse(var list: List<WeatherResponse>): Serializable

@Parcelize
data class WeatherResponse(
    var main: Main?,
    var name: String?,
    var weather: List<WeatherX>?,
    var wind:Wind?
): Parcelable

@Parcelize
data class Main(
    var humidity: Int?,
    var pressure: Int?,
    var temp: Double?,
    var temp_max: Int?,
    var temp_min: Int?
): Parcelable

@Parcelize
data class WeatherX(
    var description: String?,
    var icon: String?,
    var id: Int?,
    var main: String?
): Parcelable

@Parcelize
data class Wind(
    var deg: Int?,
    var speed: Double?
): Parcelable
