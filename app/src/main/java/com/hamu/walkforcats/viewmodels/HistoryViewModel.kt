package com.hamu.walkforcats.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hamu.walkforcats.R
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
    init {
        checkChangeCat()
    }
    val dt = LocalDate.now()
    var isFirstDisplay = false
    var isChangeCat = false
    val allMonthlyInfo: LiveData<List<MonthlyInfo>> = historyRepository.allMonthlyInfo.asLiveData()

    fun checkInfo(){
        checkIsUseDemoData()
        checkIsFirstDisplay()
    }

    private fun checkIsUseDemoData() {
        val isUseDemoData= preferenceRepository.isUseDemoData()
        if(isUseDemoData) useDemoData() else noUseDemoData()
    }

    private fun useDemoData() {
        viewModelScope.launch {
            historyRepository.insertDemoData()
        }
    }
    private fun noUseDemoData(){
        viewModelScope.launch {
            historyRepository.deleteDemoData()
        }
    }
    private fun checkIsFirstDisplay(){
      isFirstDisplay = preferenceRepository.checkFirstTimeOfHistry()
    }
    fun changeiIsFirstDisplayToFalse(){
        preferenceRepository.changeIsFirstTimeOfHistry()
    }

    fun checkChangeCat() {
       isChangeCat =  preferenceRepository.isCangeCat()
    }

    fun getRangeOfPercent(percent:Int): Int {
        var whichCatToSet: Int
        percent.let {
            if(isChangeCat) {
                if (10 >= it) { whichCatToSet = R.drawable.realcat1 }
                else if (30 >= it) {whichCatToSet = R.drawable.realcat2 }
                else if (60 >= it) {whichCatToSet = R.drawable.realcat3 }
                else if (80 >= it) {whichCatToSet= R.drawable.realcat4 }
                else {whichCatToSet = R.drawable.realcat5 }
            }else{
                if (10 >= it) { whichCatToSet = R.drawable.whitecat1 }
                else if (30 >= it) { whichCatToSet = R.drawable.whitecat2 }
                else if (60 >= it) {whichCatToSet= R.drawable.whitecat3 }
                else if (80 >= it) {whichCatToSet = R.drawable.whitecat4 }
                else {whichCatToSet = R.drawable.whitecat5 }
            }
        }
        return whichCatToSet
    }
}