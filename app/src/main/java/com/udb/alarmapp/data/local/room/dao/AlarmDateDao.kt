package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity


@Dao
interface AlarmDateDao {

    @Insert
    suspend fun addAlarmDate(item: AlarmDateEntity)

    @Query("DELETE FROM AlarmDateEntity WHERE alarmid = :alarmId")
    suspend fun deleteAlarmDateById(alarmId: String)
}