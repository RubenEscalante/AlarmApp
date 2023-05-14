package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.AlarmDateModel
import com.udb.alarmapp.data.local.model.AlarmMedicineModel
import com.udb.alarmapp.data.local.model.AlarmModel
import com.udb.alarmapp.data.local.repository.AlarmRepository
import javax.inject.Inject

class UpdateActiveUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(
        alarmId: String,
        active: Boolean
    ) {
        alarmRepository.updateActive(
            alarmId = alarmId,
            active = active
        )
    }
}