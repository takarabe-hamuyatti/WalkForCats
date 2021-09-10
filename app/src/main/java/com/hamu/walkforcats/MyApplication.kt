package com.hamu.walkforcats

import android.app.Application
import androidx.core.content.edit
import androidx.hilt.work.HiltWorkerFactory
import androidx.preference.PreferenceManager
import androidx.work.*
import com.hamu.walkforcats.utils.preferenceKey.Companion.firstInitKey
import com.hamu.walkforcats.worker.OnlyFirstDayWork
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import java.time.temporal.ChronoUnit


@HiltAndroidApp
class MyApplication :Application(),Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val isFirstInit = pref.getBoolean("Firstinitsa",true)
            if(isFirstInit) {
                pref.edit {
                    putBoolean(firstInitKey,false)
                }
                val target = LocalTime.of(23, 59)
                val minutes: Long = ChronoUnit.MINUTES.between(LocalTime.now(), target)
                //最後の5分をflextimeinterval に指定しているので、
                // 12:01　から12:06をその5分に入れて確実に保存するために23:59から7分遅延させています。
                setupOnlyFirstWork(minutes+7)
            }
        }
    }
    private fun setupOnlyFirstWork(minute:Long) {
        Timber.i("initwork")

        val request =
            OneTimeWorkRequestBuilder<OnlyFirstDayWork>()
                .addTag(WorkTag)
                .setInitialDelay(minute,TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(applicationContext).enqueue(request)
    }
    companion object{
        val WorkTag = "everydayWork"
    }
}