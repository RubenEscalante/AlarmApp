package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity
import com.udb.alarmapp.data.local.room.entities.AlarmEntity

@Dao
interface AlarmDao {
    @Insert
    suspend fun addAlarm(item: AlarmEntity)


}