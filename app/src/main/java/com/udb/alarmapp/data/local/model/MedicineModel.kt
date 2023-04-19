package com.udb.alarmapp.data.local.model

import java.util.*

data class MedicineModel(
    val id: String = UUID.randomUUID().toString(),
    var medicine:String,
    var doctorName:String,
    var stock:String
)