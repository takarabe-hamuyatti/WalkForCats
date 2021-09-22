package com.hamu.walkforcats.di

import android.content.Context
import android.content.SharedPreferences
import android.hardware.SensorManager
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.hamu.walkforcats.api.WeatherInfoService
import com.hamu.walkforcats.database.dao.AboutMonthlyInfoDao
import com.hamu.walkforcats.database.AboutHistoryDatabase
import com.hamu.walkforcats.database.dao.AboutPastLocationDao
import com.hamu.walkforcats.repository.past_location.PastLocationRepository
import com.hamu.walkforcats.repository.past_location.PastLocationRepositoryImpl
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepositoryImpl
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepository
import com.hamu.walkforcats.repository.get_weather_info.GetWeatherInfoRepositoryImpl
import com.hamu.walkforcats.repository.monthly_history.MonthlyHistoryRepository
import com.hamu.walkforcats.repository.monthly_history.MonthlyMonthlyHistoryRepositoryImpl
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepositoryImpl
import com.hamu.walkforcats.utils.UniqueId.Companion.MONTHLYINFO_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideMonthlyInfoDatabase(@ApplicationContext context: Context):AboutHistoryDatabase{
        return Room.databaseBuilder(
            context,
            AboutHistoryDatabase::class.java,
            MONTHLYINFO_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMonthlyInfoDao(db:AboutHistoryDatabase) = db.aboutMonthlyInfoDao

    @Provides
    @Singleton
    fun providePastLocationDao(db: AboutHistoryDatabase) = db.aboutPastLocationDao

    @Provides
    @Singleton
    fun provideApi():WeatherInfoService{
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/onecall?")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideSystemService(@ApplicationContext context: Context) =context.getSystemService(Context.SENSOR_SERVICE) as SensorManager



    @Provides
    @Singleton
    fun provideHistoryRepository(
        dao: AboutMonthlyInfoDao
    ): MonthlyHistoryRepository = MonthlyMonthlyHistoryRepositoryImpl(dao)

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

    @Provides
    @Singleton
    fun provideGetWeatherInfoRepository(
        weatherInfoService: WeatherInfoService
    ):GetWeatherInfoRepository = GetWeatherInfoRepositoryImpl(weatherInfoService)

    @Provides
    @Singleton
    fun providePastLocationRepository(
        dao: AboutPastLocationDao
    ):PastLocationRepository = PastLocationRepositoryImpl(dao)

}