package com.udb.alarmapp.data.local.repository

import com.udb.alarmapp.data.local.model.AlarmDateModel
import com.udb.alarmapp.data.local.model.AlarmModel
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.AlarmDateDao
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity
import com.udb.alarmapp.data.local.room.entities.AlarmEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmDateDao: AlarmDateDao
) {

    suspend fun add(alarmModel: AlarmModel, alarmDates: List<AlarmDateModel>) {
        alarmDao.addAlarm(alarmModel.toAlarmEntity())
        val alarmDateEntityList: List<AlarmDateEntity> = alarmDates.map {
            AlarmDateEntity(it.id, it.alarmid, it.date)
        }
        alarmDateEntityList.forEach { date ->
            alarmDateDao.addAlarmDate(date)
        }

    }
}

fun AlarmModel.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = this.id,
        hour = this.hour,
        ampm = this.ampm,
        days = this.days
    )
}