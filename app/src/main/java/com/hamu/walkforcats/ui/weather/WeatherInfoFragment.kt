package com.hamu.walkforcats.ui.weather

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentWeatherInfoBinding
import com.hamu.walkforcats.utils.confirmDialog
import com.hamu.walkforcats.viewmodels.WeathearInfoViewmodel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.Executor

@AndroidEntryPoint
class WeatherInfoFragment : Fragment(R.layout.fragment_weather_info) {

    private val viewmodel:WeathearInfoViewmodel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            getLocation()
        } else {
            viewmodel.checkIsPastLocationIsNull()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentWeatherInfoBinding.bind(view).let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            requestPermissionLauncher.launch(ACCESS_COARSE_LOCATION)

            viewmodel.isDisplayDaialog.observe(viewLifecycleOwner, {
                if (it) displayDialog()
            })
        }
    }

    private fun displayDialog(){
        confirmDialog(requireContext(),"",requireContext().getString(R.string.error_message)
        ) { viewmodel.hideDialog() }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener {it : Location? ->
                val longitude = it?.longitude ?: 36.0
                val latitude = it?.latitude ?: 140.0
                viewmodel.getWeatherInfo(longitude,latitude)
                viewmodel.updatePastLocation(longitude,latitude)
            }
            .addOnFailureListener{
                viewmodel.checkIsPastLocationIsNull()
            }
    }

}