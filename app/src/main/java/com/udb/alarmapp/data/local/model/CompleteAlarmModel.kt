package com.udb.alarmapp.data.local.model

data class CompleteAlarmModel(
    val id: String,
    val hour: String,
    val ampm: String,
    val sunmoon: String,
    val days: String,
    val active: Boolean,
    val medicines: List<MedicineModel>
)