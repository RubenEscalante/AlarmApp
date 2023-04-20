package com.udb.alarmapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.AlarmDateDao
import com.udb.alarmapp.data.local.room.dao.MedicineDao
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity

import com.udb.alarmapp.data.local.room.entities.AlarmEntity

import com.udb.alarmapp.data.local.room.entities.MedicineEntity

@Database(
    entities = [MedicineEntity::class, AlarmEntity::class, AlarmDateEntity::class],
    version = 1
)
abstract class AlarmAppDataBase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun alarmDao(): AlarmDao
    abstract fun alarmDateDao(): AlarmDateDao
}