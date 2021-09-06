package com.hamu.walkforcats.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.HistoryRepository
import com.hamu.walkforcats.repository.PreferenceRepository
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
    val context = application


    private val _isUseDemoData = MutableLiveData(true)
    val isUseDemoData:LiveData<Boolean>
        get() = _isUseDemoData

    val allMonthlyInfo: LiveData<List<monthlyInfo>> = historyRepository.allMonthlyInfo.asLiveData()

    fun checkIsUseDemoData() {
        _isUseDemoData.value = preferenceRepository.isUseDemoData()
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
}