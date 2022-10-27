package com.example.avitointernship.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avitointernship.databinding.WeatherItemBinding
import java.util.*

class DayWeatherAdapter : RecyclerView.Adapter<DayWeatherAdapter.DayWeatherHolder>(){

    private var dayItems: List<Daily?>? = null

    fun setDayItemsList(dayItems: List<Daily?>?) {
        this.dayItems = dayItems
    }

    class DayWeatherHolder(private val itemBinding: WeatherItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){
            fun bindWeather(day: Daily) {
                val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.FRANCE)
                val date = Date(day.dt?.times(1000L)!!)
                val result = sdf.format(date)
                itemBinding.itemDayTextView.text =
                    "Day: ${result[8]}${result[9]}.${result[5]}${result[6]}"
                itemBinding.itemTempTextView.text = "Temp: " +
                    ((day.temp?.dayTemp?.toInt())?.minus(273)).toString() + "°"
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeatherHolder {
        val itemBinding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DayWeatherHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DayWeatherHolder, position: Int) {
        holder.bindWeather(dayItems?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if(dayItems != null)
            dayItems!!.size
        else 0
    }
}