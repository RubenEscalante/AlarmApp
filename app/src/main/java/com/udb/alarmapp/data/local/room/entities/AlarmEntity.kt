package com.udb.alarmapp.data.local.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.udb.alarmapp.data.local.model.AlarmDateModel

@Entity
data class AlarmEntity(
    @PrimaryKey
    val id: String,
    var hour: String,
    var ampm: String,
    var sunmoon: String,
    var days: String,
)

