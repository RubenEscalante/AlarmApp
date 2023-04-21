package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.AlarmDateModel
import com.udb.alarmapp.data.local.model.AlarmMedicineModel
import com.udb.alarmapp.data.local.model.AlarmModel
import com.udb.alarmapp.data.local.repository.AlarmRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarmModel: AlarmModel, alarmDates: List<AlarmDateModel>, medicineList: List<AlarmMedicineModel>) {
        alarmRepository.add(alarmModel = alarmModel,alarmDates, medicineList)
    }
}