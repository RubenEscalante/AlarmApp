package com.udb.alarmapp.data.local.model

import java.util.UUID

data class AlarmMedicineModel(
    val id: String = UUID.randomUUID().toString(),
    var alarmId:String,
    var medicinesId:List<MedicineModel>,
    var doses:String
)