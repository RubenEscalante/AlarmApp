package com.udb.alarmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.udb.alarmapp.presentation.navigation.NavGraph
import com.udb.alarmapp.presentation.screens.homescreen.HomeViewModel
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesViewModel
import com.udb.alarmapp.presentation.ui.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val medicinesViewModel: MedicinesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavGraph(homeViewModel, medicinesViewModel)
                }
            }
        }
    }
}