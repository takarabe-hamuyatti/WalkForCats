package com.example.walkforcats.viewmodels

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walkforcats.repository.CatInfoRepository

class CatRoomViewModel : ViewModel() {
    private val repository:CatInfoRepository = CatInfoRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "まだ猫を獲得していません。"
    }
    val text: LiveData<String> = _text


}