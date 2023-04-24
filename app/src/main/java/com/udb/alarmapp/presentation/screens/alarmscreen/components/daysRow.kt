package com.udb.alarmapp.presentation.screens.alarmscreen.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Lifecycling
import com.udb.alarmapp.presentation.screens.alarmscreen.AlarmViewModel

@Composable
fun daysRow(alarmViewModel: AlarmViewModel) {
    val daysList = listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")
    val diasEnViewModel = alarmViewModel.selectedDays.collectAsState()

    LaunchedEffect(true) {
        diasEnViewModel.value?.forEach{day->
                alarmViewModel.diasSeleccionados.add(day)
                alarmViewModel.changeDaysSelection( alarmViewModel.diasSeleccionados)

        }
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(daysList) { day ->
            val textColor by animateColorAsState(
                if (diasEnViewModel.value?.contains(day) == true) Color.White else Color(
                    0xff312b63
                )
            )
            val backGroundColor by animateColorAsState(
                if (diasEnViewModel.value?.contains(day) == true) Color(
                    0xffb178ff
                ) else Color.White
            )
            dayBox(day = day,
                backgroundColor = backGroundColor,
                textColor = textColor,
                onClick = {
                    if (diasEnViewModel.value?.contains(day) == true) {
                        alarmViewModel.diasSeleccionados.remove(day)
                    } else {
                        alarmViewModel.diasSeleccionados.add(day)
                    }
                    alarmViewModel.changeDaysSelection( alarmViewModel.diasSeleccionados)
                })
        }
    }
}