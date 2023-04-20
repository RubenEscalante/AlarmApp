package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.repository.MedicineRepository
import javax.inject.Inject

class DeleteMedicineUseCase @Inject constructor(
    private val medicineRepository: MedicineRepository
) {
    suspend operator fun invoke(medicineModel: MedicineModel) {
        medicineRepository.delete(medicineModel)
    }
}