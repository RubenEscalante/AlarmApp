package com.udb.alarmapp.data.local.repository

import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.model.RecordModel
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.AlarmDateDao
import com.udb.alarmapp.data.local.room.dao.AlarmMedicineDao
import com.udb.alarmapp.data.local.room.dao.RecordDao
import com.udb.alarmapp.data.local.room.entities.MedicineEntity
import com.udb.alarmapp.data.local.room.entities.RecordEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecordRepository @Inject constructor(
    private val recordDao: RecordDao
) {
    suspend fun add(recordModel: RecordModel) {
        recordDao.addRecord(recordModel.toRecordEntity())
    }
}
fun RecordModel.toRecordEntity(): RecordEntity {
    return RecordEntity(
        id = this.id,
        alarmId = this.alarmId,
        date = this.date,
        hour = this.hour,
        medicines = this.medicines
    )
}
