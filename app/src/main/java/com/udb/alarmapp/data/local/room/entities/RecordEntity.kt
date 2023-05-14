package com.udb.alarmapp.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordEntity(
    @PrimaryKey
    val id: String,
    var alarmId: String,
    var date: String,
    var hour: String,
    var medicines: String
)