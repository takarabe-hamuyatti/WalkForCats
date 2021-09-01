package com.hamu.walkforcats.viewmodels

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.CatInfoRepository
import com.hamu.walkforcats.repository.HistoryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.O)
    val dt = LocalDate.now()
    val context = application

    private val _monthlyInfo = MutableLiveData<MutableList<monthlyInfo>>()
    val monthlyInfo :LiveData<MutableList<monthlyInfo>>
        get() = _monthlyInfo

    val isDataExist = Transformations.map(monthlyInfo) {
        it != null
    }

    fun getMonthlyInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            val database = getDatabase(context)
            val repository = HistoryRepository(database)
            var tmp = repository.getMonthlyInfo()
            viewModelScope.launch(Dispatchers.Main) {
                _monthlyInfo.value = tmp as MutableList<monthlyInfo>
            }
        }
    }
}