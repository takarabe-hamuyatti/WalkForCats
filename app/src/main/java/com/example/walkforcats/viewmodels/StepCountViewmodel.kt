package com.example.walkforcats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepCountViewmodel:ViewModel(){


    private var _percent = MutableLiveData<Float>()
    val percent : LiveData<Float>
        get() = _percent

    private var _count = MutableLiveData<Int>().apply {
        value = 0
    }
    val count :LiveData<Int>
         get() = _count

    fun plusCount(){
        _count.value = _count.value?.plus(1)
    }

    fun getParcent(){
        var tmp = _count.value?.toFloat()?.times(100)
        _percent.value = tmp?.div(1000)
    }


}