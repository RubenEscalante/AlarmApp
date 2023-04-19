package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor(
    private val medicineRepository: MedicineRepository
) {
    operator fun invoke(): Flow<List<MedicineModel>> = medicineRepository.medicines

}