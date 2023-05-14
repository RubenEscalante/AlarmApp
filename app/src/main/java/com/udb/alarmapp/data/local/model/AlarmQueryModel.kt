package com.udb.alarmapp.data.local.model

data class AlarmQueryModel(
    val id:String,
    val hour: String,
    val ampm: String,
    val sunmoon: String,
    val days: String,
    val active: Boolean,
    val medicineId: String = "",
    val medicine: String,
    val stock: String,
    val doctorName: String
)