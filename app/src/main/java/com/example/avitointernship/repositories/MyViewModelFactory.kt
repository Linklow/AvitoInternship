package com.example.avitointernship.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.avitointernship.presentation.WeatherViewModel

class MyViewModelFactory(private val repository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            WeatherViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}