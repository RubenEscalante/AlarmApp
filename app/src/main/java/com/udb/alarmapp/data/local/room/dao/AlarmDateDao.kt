package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity


@Dao
interface AlarmDateDao {

    @Insert
    suspend fun addAlarmDate(item: AlarmDateEntity)
}