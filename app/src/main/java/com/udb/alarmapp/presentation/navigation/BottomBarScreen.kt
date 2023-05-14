package com.udb.alarmapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.udb.alarmapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home: BottomBarScreen(
        route = Screen.Home.route,
        title = "Inicio",
        icon = R.drawable.baseline_home_24
    )
    object Medicine: BottomBarScreen(
        route = Screen.Medicine.route,
        title = "Medicinas",
        icon =  R.drawable.baseline_medical_services_24
    )
//    object Record: BottomBarScreen(
//        route = Screen.Record.route,
//        title = "Historial",
//        icon = R.drawable.archive_24
//    )

}