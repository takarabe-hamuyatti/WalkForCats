package com.hamu.walkforcats.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.HistoryRepository
import java.time.LocalDate

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.O)
    val dt = LocalDate.now()
    val context = application

    val _isDataExist = MutableLiveData(false)
    val isDataExist:LiveData<Boolean>
        get() = _isDataExist

    val Dao= getDatabase(context).aboutMonthlyInfoDao
    val repository = HistoryRepository(Dao)


    val allMonthlyInfo: LiveData<List<monthlyInfo>> = repository.allMonthlyInfo.asLiveData()

}