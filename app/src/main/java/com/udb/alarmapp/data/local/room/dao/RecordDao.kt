package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.udb.alarmapp.data.local.room.entities.MedicineEntity
import com.udb.alarmapp.data.local.room.entities.RecordEntity

@Dao
interface RecordDao {
    @Insert
    suspend fun addRecord(item: RecordEntity)
}