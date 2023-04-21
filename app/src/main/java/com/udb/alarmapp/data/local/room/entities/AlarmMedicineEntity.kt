package com.udb.alarmapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmMedicineEntity(
    @PrimaryKey
    val id: String,
    var alarmid: String,
    var medicineId: String
)