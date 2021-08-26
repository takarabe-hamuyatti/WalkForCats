package com.hamu.walkforcats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamu.walkforcats.repository.CatInfoRepository

class ChartViewModel : ViewModel() {

    private val repository: CatInfoRepository = CatInfoRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "歩きなさい！！"
    }
    val text: LiveData<String> = _text


}