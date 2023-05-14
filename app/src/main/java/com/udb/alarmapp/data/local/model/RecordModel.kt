package com.udb.alarmapp.data.local.model

import java.util.UUID

data class RecordModel(
    val id: String = UUID.randomUUID().toString(),
    var alarmId: String,
    var date: String,
    var hour: String,
    var medicines: String
)