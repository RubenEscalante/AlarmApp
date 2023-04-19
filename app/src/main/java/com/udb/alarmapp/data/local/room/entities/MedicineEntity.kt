package com.udb.alarmapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicineEntity(
    @PrimaryKey
    val id: String,
    var medicine: String,
    var doctorName: String,
    var stock: String
)