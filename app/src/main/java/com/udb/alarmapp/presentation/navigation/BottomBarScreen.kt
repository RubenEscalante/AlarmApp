package com.udb.alarmapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = Screen.Home.route,
        title = "Inicio",
        icon = Icons.Default.Home
    )
    object Medicine: BottomBarScreen(
        route = Screen.Medicine.route,
        title = "Medicinas",
        icon = Icons.Default.Email
    ) object Record: BottomBarScreen(
        route = Screen.Record.route,
        title = "Historial",
        icon = Icons.Default.Star
    )

}