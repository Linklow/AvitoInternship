package com.example.avitointernship.services

import com.example.avitointernship.models.CityResponse
import com.example.avitointernship.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("/data/3.0/onecall?exclude=hourly,minutely,alerts&appid=7c25d52f19afb3a3d7cca94ae847a397")
        fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherResponse>

    @GET("geo/1.0/direct?limit=5&appid=7c25d52f19afb3a3d7cca94ae847a397")
    fun getCity(@Query("q") city: String): Call<List<CityResponse>>
}