package com.udb.alarmapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.MedicineDao
import com.udb.alarmapp.data.local.room.entities.MedicineEntity

@Database(entities = [MedicineEntity::class], version = 1)
abstract class AlarmAppDataBase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun alarmDao(): AlarmDao
}