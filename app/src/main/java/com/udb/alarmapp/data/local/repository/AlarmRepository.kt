package com.udb.alarmapp.data.local.repository

import androidx.room.Transaction
import com.udb.alarmapp.data.local.model.AlarmDateModel
import com.udb.alarmapp.data.local.model.AlarmMedicineModel
import com.udb.alarmapp.data.local.model.AlarmModel
import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.data.local.model.DataListQueryModel
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.room.dao.AlarmDao
import com.udb.alarmapp.data.local.room.dao.AlarmDateDao
import com.udb.alarmapp.data.local.room.dao.AlarmMedicineDao
import com.udb.alarmapp.data.local.room.entities.AlarmDateEntity
import com.udb.alarmapp.data.local.room.entities.AlarmEntity
import com.udb.alarmapp.data.local.room.entities.AlarmMedicineEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmDateDao: AlarmDateDao,
    private val alarmMedicineDao: AlarmMedicineDao
) {
    val dateList: Flow<List<DataListQueryModel>> = alarmDao.getDatesList()

    val alarms: Flow<List<CompleteAlarmModel>> =
        alarmDao.getAlarms().map { items ->
            items.groupBy { it.id }.map { (_, alarms) ->
                val firstAlarm = alarms.first()
                CompleteAlarmModel(
                    firstAlarm.id,
                    formatTimeTo12HourFormat(firstAlarm.hour),
                    firstAlarm.ampm,
                    firstAlarm.sunmoon,
                    firstAlarm.days,
                    firstAlarm.active,
                    alarms.map {
                        MedicineModel(
                            id = it.medicineId,
                            medicine = it.medicine,
                            stock = it.stock,
                            doctorName = it.doctorName
                        )
                    }
                )
            }
        }

    @Transaction
    suspend fun add(
        alarmModel: AlarmModel,
        alarmDates: List<AlarmDateModel>,
        medicineList: List<AlarmMedicineModel>
    ) {
        val alarmDateEntityList: List<AlarmDateEntity> = alarmDates.map {
            AlarmDateEntity(it.id, it.alarmid, it.date)
        }
        alarmDateEntityList.forEach { date ->
            alarmDateDao.addAlarmDate(date)
        }
        val alarmMedicineList: List<AlarmMedicineEntity> = medicineList.map {
            AlarmMedicineEntity(it.id, it.alarmId, it.medicinesId)
        }
        alarmMedicineList.forEach { medicine ->
            alarmMedicineDao.addAlarmMedine(medicine)
        }
        alarmDao.addAlarm(alarmModel.toAlarmEntity())

    }

    @Transaction
    suspend fun deleteAlarmById(alarmId: String) {
        alarmDao.deleteAlarmById(alarmId = alarmId)
        alarmDateDao.deleteAlarmDateById(alarmId = alarmId)
        alarmMedicineDao.deleteAlarmMedicineById(alarmId = alarmId)
    }

    @Transaction
    suspend fun updateAlarmById(
        alarmId: String,
        alarmModel: AlarmModel,
        alarmDates: MutableList<AlarmDateModel>,
        medicineList: List<AlarmMedicineModel>
    ) {
        deleteAlarmById(alarmId)
        val alarmDateEntityList: List<AlarmDateEntity> = alarmDates.map { it ->
            AlarmDateEntity(it.id, alarmId, it.date)
        }
        alarmDateEntityList.forEach { date ->
            alarmDateDao.addAlarmDate(date)
        }

        val alarmMedicineList: List<AlarmMedicineEntity> = medicineList.map {
            AlarmMedicineEntity(it.id, it.alarmId, it.medicinesId)
        }
        alarmMedicineList.forEach { medicine ->
            alarmMedicineDao.addAlarmMedine(medicine)
        }
        alarmDao.addAlarm(alarmModel.toAlarmEntity())

    }

    @Transaction
    suspend fun updateActive(
        alarmId: String,
        active: Boolean,
    ) {
        alarmDao.updateActive(alarmId, active)
    }

    private fun formatTimeTo12HourFormat(timeString: String): String {
        val format24 = SimpleDateFormat("HH:mm", Locale.getDefault())
        val format12 = SimpleDateFormat("hh:mm", Locale.getDefault())
        val time = format24.parse(timeString)
        return format12.format(time!!)
    }

    fun getCompleteAlarmById(alarmId: String): Flow<CompleteAlarmModel> {
        return alarms.map { it -> it.firstOrNull { it.id == alarmId } }.filterNotNull()
    }
}

fun AlarmModel.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = this.id,
        hour = this.hour,
        ampm = this.ampm,
        sunmoon = this.sunmoon,
        days = this.days,
        active = this.active
    )
}