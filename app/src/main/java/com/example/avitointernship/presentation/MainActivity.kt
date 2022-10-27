package com.example.avitointernship.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.avitointernship.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, WeatherFragment.newInstance())
                .commitNow()
        }
    }
}