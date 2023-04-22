package com.udb.alarmapp.di

import android.content.Context

import androidx.room.Room
import com.udb.alarmapp.data.local.room.AlarmAppDataBase
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.AlarmDateDao
import com.udb.alarmapp.data.local.room.dao.AlarmMedicineDao
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
        return Room.databaseBuilder(appContext, AlarmAppDataBase::class.java, "13AlarmAppDataBase")
            .build()
    }

    @Provides
    fun provideMedicineDao(alarmAppDataBase: AlarmAppDataBase): MedicineDao {
        return alarmAppDataBase.medicineDao()
    }

    @Provides
    fun provideAlarmDao(alarmAppDataBase: AlarmAppDataBase): AlarmDao {
        return alarmAppDataBase.alarmDao()
    }

    @Provides
    fun provideAlarmDateDao(alarmAppDataBase: AlarmAppDataBase): AlarmDateDao {
        return alarmAppDataBase.alarmDateDao()
    }

    @Provides
    fun provideAlarmMedicineDao(alarmAppDataBase: AlarmAppDataBase): AlarmMedicineDao {
        return alarmAppDataBase.alarmMedicineDao()
    }

}