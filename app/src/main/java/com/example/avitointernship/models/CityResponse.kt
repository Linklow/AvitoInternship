package com.example.avitointernship.models

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("name")
    val name: String?,

    @SerializedName("lat")
    var lat: Double?,

    @SerializedName("lon")
    var lon: Double?
)
