package com.hamu.walkforcats.hilt

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.checkIsChangeOfDateDatabase
import com.hamu.walkforcats.database.monthlyInfoDatabase
import com.hamu.walkforcats.repository.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun ProvideSharedPreference(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideMonthlyInfoDatabase(@ApplicationContext context: Context):monthlyInfoDatabase{
        return Room.databaseBuilder(
            context,
            monthlyInfoDatabase::class.java,
            "monthlyInfoDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMonthlyInfoDao(db:monthlyInfoDatabase) = db.aboutMonthlyInfoDao

    @Provides
    @Singleton
    fun providecheckIsChangeOfDateDatabase(@ApplicationContext context: Context):checkIsChangeOfDateDatabase{
        return Room.databaseBuilder(
            context,
            checkIsChangeOfDateDatabase::class.java,
            "checkIsChangeOfDateDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideaboutIsChangeOfDateDao(db:checkIsChangeOfDateDatabase) = db.aboutIsChangeOfDate

    @Provides
    @Singleton
    fun provideHistoryRepository(
        dao:aboutMonthlyInfoDao
    ):HistoryRepository = HistoryRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePreferenceRepository(
        pref:SharedPreferences
    ):PreferenceRepository = PreferenceRepositoryImpl(pref)

    @Provides
    @Singleton
    fun provideCreateFinishedMonthRepository(
        dao: aboutMonthlyInfoDao
    ):CreateFinishedMonthRepository = CreateFinishedMonthRepositoryImpl(dao)

}