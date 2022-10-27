package com.example.avitointernship.presentation

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avitointernship.databinding.FragmentWeatherBinding
import com.example.avitointernship.models.Daily
import com.example.avitointernship.models.DayWeatherAdapter
import com.example.avitointernship.repositories.MyViewModelFactory
import com.example.avitointernship.repositories.WeatherRepository
import com.google.android.gms.location.*
import java.text.DecimalFormat

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var contextWeather: Context
    private lateinit var recyclerAdapter: DayWeatherAdapter

    private val dm = DecimalFormat("0.00")
    var latitude = 0.00
    var longitude = 0.00

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextWeather = context
    }

    private fun fetchLocation(context: Context) {
        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(requireActivity(),
               arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if(it != null) {
                latitude = dm.format(it.longitude).toDouble()
                longitude = dm.format(it.longitude).toDouble()
                viewModel.makeAPICallForWeather(latitude,longitude)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contextWeather)
        viewModel = ViewModelProvider(this,
            MyViewModelFactory(WeatherRepository()))[WeatherViewModel::class.java]

        initRecyclerView()
        initViewModel()

        binding.getCurrentWeatherBtn.setOnClickListener {
            fetchLocation(contextWeather)
            }

        binding.getWeatherBtn.setOnClickListener {
            viewModel.makeAPICallForCity(binding.cityInput.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        viewModel.getWeatherLiveDataObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                setTempTextViews(it.current?.temp,it.current?.feelsLikeTemp)
                setRecyclerView(it.daily)
            } else {
                Toast.makeText(requireContext(), "Error in getting temperature", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getCityLiveDataObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                latitude = dm.format(it[0].lat).toDouble()
                longitude = dm.format(it[0].lon).toDouble()
                viewModel.makeAPICallForWeather(latitude,longitude)
            } else {
                Toast.makeText(requireContext(), "Error in getting temperature", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvWeather.layoutManager = LinearLayoutManager(contextWeather,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerAdapter = DayWeatherAdapter()
        binding.rvWeather.adapter = recyclerAdapter
    }

    private fun setTempTextViews(current: Double?, feelsLike: Double?) {
        val currentTemp = dm.format(current)
            .toDouble().toInt() - 273
        val currentFeelsLikeTemp = dm.format(feelsLike)
            .toDouble().toInt() - 273
        binding.temperatureTextView.text = "Temp: $currentTemp°"
        binding.feelsLikeTemperatureTextView.text = "Feels like: $currentFeelsLikeTemp°"
    }

    private fun setRecyclerView(list: List<Daily?>?) {
        recyclerAdapter.setDayItemsList(list)
        recyclerAdapter.notifyDataSetChanged()
    }
}