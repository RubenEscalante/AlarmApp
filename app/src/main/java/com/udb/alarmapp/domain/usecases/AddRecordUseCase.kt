package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.model.RecordModel
import com.udb.alarmapp.data.local.repository.MedicineRepository
import com.udb.alarmapp.data.local.repository.RecordRepository
import javax.inject.Inject

class AddRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(recordModel: RecordModel) {
        recordRepository.add(recordModel)
    }
}