package com.hamu.walkforcats.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hamu.walkforcats.database.MonthlyInfo
import com.hamu.walkforcats.repository.history.HistoryRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    application: Application,
    private val historyRepository: HistoryRepository,
    private val preferenceRepository: PreferenceRepository
) : AndroidViewModel(application) {
    val dt = LocalDate.now()

    var isFirstDisplay = false

    val allMonthlyInfo: LiveData<List<MonthlyInfo>> = historyRepository.allMonthlyInfo.asLiveData()

    fun checkInfo(){
        checkIsUseDemoData()
        checkIsFirstDisplay()
    }

    fun checkIsUseDemoData() {
        val isUseDemoData= preferenceRepository.isUseDemoData()
        if(isUseDemoData) useDemoData() else noUseDemoData()
    }

    fun useDemoData() {
        viewModelScope.launch {
            historyRepository.insertDemoData()
        }
    }
    fun noUseDemoData(){
        viewModelScope.launch {
            historyRepository.deleteDemoData()
        }
    }
    fun checkIsFirstDisplay(){
      isFirstDisplay = preferenceRepository.checkFirstTimeOfHistry()
    }
    fun changeiIsFirstDisplayToFalse(){
        preferenceRepository.changeIsFirstTimeOfHistry()
    }

    fun checkChangeCat(): Boolean {
       return preferenceRepository.isCangeCat()
    }
}