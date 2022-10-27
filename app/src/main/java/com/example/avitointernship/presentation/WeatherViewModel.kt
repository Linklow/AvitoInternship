package com.example.avitointernship.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avitointernship.models.CityResponse
import com.example.avitointernship.models.WeatherResponse
import com.example.avitointernship.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    var weatherLiveData: MutableLiveData<WeatherResponse> = MutableLiveData()
    var cityLiveData: MutableLiveData<List<CityResponse>> = MutableLiveData()

    fun getWeatherLiveDataObserver(): MutableLiveData<WeatherResponse> {
        return weatherLiveData
    }
    fun makeAPICallForWeather(lon: Double, lat: Double) {
        val response = repository.getWeather(lon,lat)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                response.enqueue(object: Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        weatherLiveData.postValue(response.body())
                    }
                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        Log.e("callFailure",t.message.toString())
                    }
                })
            }
        }
    }

    fun getCityLiveDataObserver(): MutableLiveData<List<CityResponse>> {
        return cityLiveData
    }
    fun makeAPICallForCity(city: String) {
        val response = repository.getCity(city)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                response.enqueue(object: Callback<List<CityResponse>> {
                    override fun onResponse(call: Call<List<CityResponse>>, response: Response<List<CityResponse>>) {
                        cityLiveData.postValue(response.body())
                    }
                    override fun onFailure(call: Call<List<CityResponse>>, t: Throwable) {
                        Log.e("callFailure",t.message.toString())
                    }
                })
            }
        }
    }
}