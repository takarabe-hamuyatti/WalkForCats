package com.hamu.walkforcats.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentWeatherInfoBinding
import com.hamu.walkforcats.utils.confirmDialog
import com.hamu.walkforcats.viewmodels.WeathearInfoViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class WeatherInfoFragment : Fragment(R.layout.fragment_weather_info) {

    private val viewmodel:WeathearInfoViewmodel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentWeatherInfoBinding.bind(view).let{
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                    viewmodel.checkIsPastLocationIsNull()
            }else{
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        val longitude = it.longitude
                        val latitude = it.latitude
                        viewmodel.getWeatherInfo(longitude,latitude)
                        viewmodel.updatePastLocation(longitude,latitude)
                    }
                    .addOnFailureListener{
                        viewmodel.checkIsPastLocationIsNull()
                    }
            }
        }
        viewmodel.isDisplayDaialog.observe(viewLifecycleOwner,{
            if(it)displayDialog()
        })
    }

    private fun displayDialog(){
        confirmDialog(requireContext(),"",requireContext().getString(R.string.error_message)
        ) { viewmodel.hideDialog() }
    }
}