package com.example.avitointernship.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("timezone")
    val timeZone: String?,

    @SerializedName("current")
    var current: Current?,

    @SerializedName("daily")
    var daily: List<Daily?>?
)

data class Current (
    @SerializedName("temp")
    var temp: Double?,

    @SerializedName("feels_like")
    var feelsLikeTemp: Double?
)

data class Daily (
    @SerializedName("dt")
    var dt: Int?,

    @SerializedName("temp")
    var temp: Temp?
)

data class Temp (
    @SerializedName("day")
    var dayTemp: Double?
)
