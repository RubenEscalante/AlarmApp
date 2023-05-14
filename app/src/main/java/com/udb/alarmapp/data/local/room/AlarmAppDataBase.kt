package com.udb.alarmapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.AlarmDateDao
import com.udb.alarmapp.data.local.room.dao.AlarmMedicineDao
import com.udb.alarmapp.data.local.room.dao.MedicineDao
import com.udb.alarmapp.data.local.room.dao.RecordDao
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity

import com.udb.alarmapp.data.local.room.entities.AlarmEntity
import com.udb.alarmapp.data.local.room.entities.AlarmMedicineEntity

import com.udb.alarmapp.data.local.room.entities.MedicineEntity
import com.udb.alarmapp.data.local.room.entities.RecordEntity

@Database(
    entities = [
        MedicineEntity::class,
        AlarmEntity::class,
        AlarmDateEntity::class,
        AlarmMedicineEntity::class,
        RecordEntity::class
    ],
    version = 1
)
abstract class AlarmAppDataBase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun alarmDao(): AlarmDao
    abstract fun alarmDateDao(): AlarmDateDao
    abstract fun alarmMedicineDao(): AlarmMedicineDao
    abstract fun recordDao(): RecordDao

}