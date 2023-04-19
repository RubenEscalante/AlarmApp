package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.udb.alarmapp.data.local.room.entities.MedicineEntity

@Dao
interface AlarmDao {
    @Insert
    suspend fun addAlarm(item: MedicineEntity)
}