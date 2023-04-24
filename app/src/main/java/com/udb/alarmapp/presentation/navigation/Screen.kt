package com.udb.alarmapp.presentation.navigation

import com.example.desafioii.ALARM
import com.example.desafioii.HOME
import com.example.desafioii.MEDICINE
import com.example.desafioii.RECORD
import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.data.local.model.MedicineModel


sealed class Screen(val route: String) {
    object Home : Screen(HOME)
    object Alarm : Screen(ALARM)
    object Medicine : Screen(MEDICINE)
    object Record : Screen(RECORD)

}
