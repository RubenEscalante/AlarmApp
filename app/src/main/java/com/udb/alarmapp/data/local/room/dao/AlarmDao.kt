package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.udb.alarmapp.data.local.model.AlarmQueryModel
import com.udb.alarmapp.data.local.room.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Transaction
    @Query("SELECT a.*, k.id as medicineId, k.medicine,k.doctorName,k.stock  FROM AlarmEntity a LEFT JOIN AlarmMedicineEntity f ON a.id = f.alarmid LEFT JOIN MedicineEntity k ON f.medicineId = k.id")
    fun getAlarms(): Flow<List<AlarmQueryModel>>

    @Insert
    suspend fun addAlarm(item: AlarmEntity)

    @Query("DELETE FROM AlarmEntity WHERE id = :alarmId")
    suspend fun deleteAlarmById(alarmId: String)


}