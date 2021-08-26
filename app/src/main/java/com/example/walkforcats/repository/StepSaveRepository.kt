package com.example.walkforcats.repository

import com.example.walkforcats.database.StepDatabase
import com.example.walkforcats.database.DaylyStep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StepSaveRepository (private val database: StepDatabase){
    fun saveStep(newDaylyStep:DaylyStep){
        database.aboutStepsDao.insert(newDaylyStep)
    }
}