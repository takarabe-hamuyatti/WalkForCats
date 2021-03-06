package com.hamu.walkforcats.ui.weather

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentWeatherInfoBinding
import com.hamu.walkforcats.utils.confirmDialog
import com.hamu.walkforcats.viewmodels.WeathearInfoViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather_info.*
import timber.log.Timber

@AndroidEntryPoint
class WeatherInfoFragment : Fragment(R.layout.fragment_weather_info) {

    private val viewmodel:WeathearInfoViewmodel by viewModels()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            Timber.i("granted")
            getLocation()
        } else {
            Timber.i("notgranted")
            viewmodel.checkIsPastLocationIsNull()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentWeatherInfoBinding.bind(view).let {
            requestPermissionLauncher.launch(ACCESS_COARSE_LOCATION)
            loading.visibility = View.VISIBLE
            viewmodel.isDisplayDialog.observe(viewLifecycleOwner, {
                if (it) {
                    displayDialog()
                    loading.visibility = View.INVISIBLE
                }
            })
            viewmodel.weatherList.observe(viewLifecycleOwner,{

            })
        }
    }
    private fun displayDialog(){
        confirmDialog(requireContext(),"",requireContext().getString(R.string.error_message)
        ) { viewmodel.hideDialog() }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                viewmodel.decideWorks(it)
            }
            .addOnFailureListener{
                //??????????????????????????????????????????????????????????????????????????????????????????????????????
                viewmodel.checkIsPastLocationIsNull()
            }
    }
}