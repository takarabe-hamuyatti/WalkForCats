package com.example.walkforcats.repository

import androidx.preference.PreferenceManager
import com.example.walkforcats.database.StepDatabase
import com.example.walkforcats.database.StepEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class StepSaveRepository (private val database: StepDatabase){
    suspend fun saveStep(newStep:StepEntity){
        withContext(Dispatchers.IO){
            database.Dao.insert(newStep)
        }
    }
}