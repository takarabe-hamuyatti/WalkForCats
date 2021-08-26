package com.hamu.walkforcats.repository

import com.hamu.walkforcats.database.StepDatabase
import com.hamu.walkforcats.database.DaylyStep

class StepSaveRepository (private val database: StepDatabase){
    fun saveStep(newDaylyStep:DaylyStep){
        database.aboutStepsDao.insert(newDaylyStep)
    }
}