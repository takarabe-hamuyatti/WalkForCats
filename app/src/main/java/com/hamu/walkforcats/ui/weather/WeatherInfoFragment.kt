package com.hamu.walkforcats.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentWeatherInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoFragment() : Fragment(R.layout.fragment_weather_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentWeatherInfoBinding.bind(view)
    }
}