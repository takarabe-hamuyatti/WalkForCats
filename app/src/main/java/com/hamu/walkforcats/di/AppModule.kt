package com.hamu.walkforcats.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.hamu.walkforcats.database.AboutMonthlyInfoDao
import com.hamu.walkforcats.database.monthlyInfoDatabase
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepositoryImpl
import com.hamu.walkforcats.repository.history.HistoryRepository
import com.hamu.walkforcats.repository.history.HistoryRepositoryImpl
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepositoryImpl
import com.hamu.walkforcats.utils.uniqueid.Companion.databaseName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideMonthlyInfoDatabase(@ApplicationContext context: Context):monthlyInfoDatabase{
        return Room.databaseBuilder(
            context,
            monthlyInfoDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideMonthlyInfoDao(db:monthlyInfoDatabase) = db.aboutMonthlyInfoDao

    @Provides
    @Singleton
    fun provideHistoryRepository(
        dao: AboutMonthlyInfoDao
    ): HistoryRepository = HistoryRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePreferenceRepository(
        pref:SharedPreferences
    ): PreferenceRepository = PreferenceRepositoryImpl(pref)

    @Provides
    @Singleton
    fun provideCreateFinishedMonthRepository(
        dao: AboutMonthlyInfoDao
    ): CreateFinishedMonthRepository = CreateFinishedMonthRepositoryImpl(dao)

}