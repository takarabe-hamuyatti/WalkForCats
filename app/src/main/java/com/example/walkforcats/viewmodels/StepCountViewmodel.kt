package com.example.walkforcats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepCountViewmodel :ViewModel(){


    private var _percent = MutableLiveData<Float>()
    val percent : LiveData<Float>
        get() = _percent

    private var _steps = MutableLiveData<Int>().apply {
        value = 0
    }
    val steps :LiveData<Int>
         get() = _steps

    fun plusStep(){
        _steps.value = _steps.value?.plus(1)
    }

    fun getParcent(){
        var tmp = _steps.value?.toFloat()?.times(100)
        _percent.value = tmp?.div(1000)
    }


}