package com.hamu.walkforcats.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentWeatherInfoBinding
import com.hamu.walkforcats.viewmodels.WeathearInfoViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoFragment() : Fragment(R.layout.fragment_weather_info) {

    private val viewmodel:WeathearInfoViewmodel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentWeatherInfoBinding.bind(view)
    }
}