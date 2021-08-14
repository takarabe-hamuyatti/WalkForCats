package com.example.walkforcats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walkforcats.repository.StepCountRepository

class TodaysStepViewModel : ViewModel() {

    private val repository :StepCountRepository = StepCountRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "今日の歩数だよ！"
    }
    val text: LiveData<String> = _text
}