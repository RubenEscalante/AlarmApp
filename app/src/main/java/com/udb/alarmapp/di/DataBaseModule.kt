package com.udb.alarmapp.di

import android.content.Context

import androidx.room.Room
import com.udb.alarmapp.data.local.room.AlarmAppDataBase
import com.udb.alarmapp.data.local.room.dao.MedicineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideAlarmDataBase(@ApplicationContext appContext: Context): AlarmAppDataBase {
        return Room.databaseBuilder(appContext, AlarmAppDataBase::class.java, "AlarmAppDataBase")
            .build()
    }

    @Provides
    fun provideMedicineDao(alarmAppDataBase: AlarmAppDataBase): MedicineDao {
        return alarmAppDataBase.medicineDao()
    }

}