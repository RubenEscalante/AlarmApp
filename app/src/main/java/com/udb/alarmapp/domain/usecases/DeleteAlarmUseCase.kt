package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.repository.AlarmRepository
import com.udb.alarmapp.data.local.repository.MedicineRepository
import javax.inject.Inject

class DeleteAlarmUseCase@Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarmId: String) {
        alarmRepository.deleteAlarmById(alarmId)
    }
}