package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.udb.alarmapp.data.local.model.AlarmQueryModel
import com.udb.alarmapp.data.local.model.DataListQueryModel
import com.udb.alarmapp.data.local.room.entities.AlarmEntity
import com.udb.alarmapp.data.local.room.entities.AlarmMedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Transaction
    @Query("SELECT a.*, k.id as medicineId, k.medicine,k.doctorName,k.stock  FROM AlarmEntity a LEFT JOIN AlarmMedicineEntity f ON a.id = f.alarmid LEFT JOIN MedicineEntity k ON f.medicineId = k.id")
    fun getAlarms(): Flow<List<AlarmQueryModel>>
    @Transaction
    @Query("SELECT a.id, a.alarmid, a.date, f.hour FROM AlarmDateEntity a LEFT JOIN AlarmEntity f ON alarmid = f.id WHERE f.active = 1 ORDER BY a.date,f.hour ASC")
    fun getDatesList(): Flow<List<DataListQueryModel>>

    @Insert
    suspend fun addAlarm(item: AlarmEntity)

    @Query("DELETE FROM AlarmEntity WHERE id = :alarmId")
    suspend fun deleteAlarmById(alarmId: String)

    @Update
    suspend fun updateAlarm(item: AlarmEntity)

    @Query("UPDATE AlarmEntity SET active = :active WHERE id = :alarmId")
    suspend fun updateActive(alarmId: String, active: Boolean)

}