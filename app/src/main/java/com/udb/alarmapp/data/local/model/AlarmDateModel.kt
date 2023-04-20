package com.udb.alarmapp.data.local.model

import java.util.UUID

data class AlarmDateModel(
    val id: String = UUID.randomUUID().toString(),
    val alarmid: String,
    var date:String
)