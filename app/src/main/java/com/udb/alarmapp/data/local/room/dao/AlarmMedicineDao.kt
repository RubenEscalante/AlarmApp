package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udb.alarmapp.data.local.room.entities.AlarmMedicineEntity

@Dao
interface AlarmMedicineDao {

    @Insert
    suspend fun addAlarmMedine(item: AlarmMedicineEntity)

    @Query("DELETE FROM AlarmMedicineEntity WHERE alarmid = :alarmId")
    suspend fun deleteAlarmMedicineById(alarmId: String)

    @Update
    suspend fun updateAlarmMedicine(item: AlarmMedicineEntity)
}