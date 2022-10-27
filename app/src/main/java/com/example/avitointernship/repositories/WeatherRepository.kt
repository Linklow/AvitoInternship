package com.example.avitointernship.repositories

import com.example.avitointernship.models.CityResponse
import com.example.avitointernship.models.WeatherResponse
import com.example.avitointernship.services.WeatherApiInterface
import com.example.avitointernship.services.WeatherApiService
import retrofit2.Call

class WeatherRepository {
     fun getWeather(lat: Double, lon: Double): Call<WeatherResponse> {
        val retroInstance = WeatherApiService.getInstance()
        val retroService = retroInstance.create(WeatherApiInterface::class.java)
        return retroService.getWeather(lat,lon)
    }

    fun getCity(city: String): Call<List<CityResponse>> {
        val retroInstance = WeatherApiService.getInstance()
        val retroService = retroInstance.create(WeatherApiInterface::class.java)
        return retroService.getCity(city)
    }
}