package com.udb.alarmapp.presentation.navigation

import com.example.desafioii.ALARM
import com.example.desafioii.HOME
import com.example.desafioii.MEDICINE
import com.example.desafioii.RECORD


sealed class Screen(val route: String) {
    object Home : Screen(HOME)
    object Alarm : Screen(ALARM)
    object Medicine : Screen(MEDICINE)
    object Record : Screen(RECORD)
//    object FirstApp : Screen(FIRSTAPP)
//    object FirstAppAverage : Screen("$FIRSTAPPAVERAGE/{id}") {
//        fun createRoute(id: studentFirstApp) = "$FIRSTAPPAVERAGE/$id"
//    }
}
