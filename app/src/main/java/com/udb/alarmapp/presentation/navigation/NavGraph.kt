package com.udb.alarmapp.presentation.navigation

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.udb.alarmapp.presentation.screens.alarmscreen.AlarmScreen
import com.udb.alarmapp.presentation.screens.alarmscreen.AlarmViewModel
import com.udb.alarmapp.presentation.screens.homescreen.HomeScreen
import com.udb.alarmapp.presentation.screens.homescreen.HomeViewModel
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesScreen
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesViewModel
import com.udb.alarmapp.presentation.screens.recordScreen.RecordScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    homeViewModel: HomeViewModel,
    medicinesViewModel: MedicinesViewModel,
    alarmViewModel: AlarmViewModel
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Medicine,
        BottomBarScreen.Record
    )
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(text = screen.title) },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = "Navigation Icon"
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    homeViewModel,
                    onNavigateToAlarm = {
                        navController.navigate(Screen.Alarm.route)
                    })
            }
            composable(Screen.Medicine.route) { MedicinesScreen(medicinesViewModel) }
            composable(Screen.Alarm.route) { AlarmScreen(medicinesViewModel, alarmViewModel) }
            composable(Screen.Record.route) { RecordScreen() }
        }
    }

}