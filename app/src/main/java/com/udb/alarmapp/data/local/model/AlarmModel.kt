package com.udb.alarmapp.data.local.model

import java.util.UUID

data class AlarmModel(
    val id: String = UUID.randomUUID().toString(),
    var hour: String,
    var ampm: String,
    var days: String,
    val sunmoon: String
)