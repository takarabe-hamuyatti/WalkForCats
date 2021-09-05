package com.hamu.walkforcats.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.HistoryRepository
import com.hamu.walkforcats.repository.PreferenceRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    val dt = LocalDate.now()
    val context = application

    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val preferenceRepository= PreferenceRepository(pref)

    private val _isUseDemoData = MutableLiveData(true)
    val isUseDemoData:LiveData<Boolean>
        get() = _isUseDemoData

    private val dao= getDatabase(context).aboutMonthlyInfoDao
    private val historyRepository = HistoryRepository(dao)

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