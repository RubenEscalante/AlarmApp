package com.udb.alarmapp.data.local.repository

import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.room.dao.MedicineDao
import com.udb.alarmapp.data.local.room.entities.MedicineEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicineRepository @Inject constructor(
    private val medicineDao: MedicineDao
) {
    val medicines: Flow<List<MedicineModel>> =
        medicineDao.getMedicines().map { items ->
            items.map {
                MedicineModel(it.id, it.medicine, it.doctorName, it.stock)
            }
        }

    suspend fun add(medicineModel: MedicineModel) {
        medicineDao.addMedicine(
            MedicineEntity(
                id = medicineModel.id,
                medicine = medicineModel.medicine,
                stock = medicineModel.stock,
                doctorName = medicineModel.doctorName
            )
        )
    }
}