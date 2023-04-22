package com.udb.alarmapp.presentation.screens.alarmscreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.presentation.components.TimeFormat
import com.udb.alarmapp.presentation.components.WheelTimePicker
import com.udb.alarmapp.presentation.screens.alarmscreen.components.daysRow
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesViewModel
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone


@Composable
fun AlarmScreen(
    medicinesViewModel: MedicinesViewModel,
    alarmViewModel: AlarmViewModel,
    onNavigateToHome: () -> Unit
) {
    val myAlarms = alarmViewModel.selectedMedicines.observeAsState(initial = emptyList())
    val myMedicines = medicinesViewModel.medicines.collectAsState(initial = emptyList())
    val enableButton = remember { mutableStateOf(false) }
    enableButton.value = myAlarms.value.isNotEmpty()

    DisposableEffect(Unit) {
        onDispose {
            alarmViewModel.clearScreenState()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentAlignment = Alignment.Center
        ) {
            WheelTimePicker(timeFormat = TimeFormat.AM_PM) { snappedTime ->
                alarmViewModel.addHour(snappedTime.toString())
            }
        }
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                daysState(alarmViewModel)
                calendar()
            }
            daysRow(alarmViewModel)
            selectionMedicine(myMedicines, alarmViewModel)
            aditionalNotes()
            Button(onClick = {
                alarmViewModel.addAlarm()
                enableButton.value = false
                onNavigateToHome()
            }, enabled = enableButton.value) {
                Text(text = "Agregar")
            }
        }

    }
}

@Composable
fun aditionalNotes() {
    Text(text = "Nota Adicional")
}

@Composable
fun selectionMedicine(myMedicines: State<List<MedicineModel>>, alarmViewModel: AlarmViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Medicinas")
        LazyColumn {
            items(
                myMedicines.value,
                key = { it.id }) { medicine ->
                medicineCard(medicine, alarmViewModel)
            }
        }
    }
}

@Composable
fun medicineCard(medicine: MedicineModel, alarmViewModel: AlarmViewModel) {
    var isCheked = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = medicine.medicine)
        Checkbox(
            checked = isCheked.value, onCheckedChange = {
                isCheked.value = !isCheked.value
                alarmViewModel.updateSelectedMedicines(isCheked.value, medicine = medicine)
            }, colors = CheckboxDefaults.colors(
                checkedColor = Color(0xffb178ff),
                uncheckedColor = Color.LightGray
            )
        )
    }

}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun calendar() {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false

//                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"


                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = "Seleccionar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState, dateValidator = { utcDateInMills ->
                val currentDate =
                    LocalDate.now() // Obtiene la fecha actual en la zona horaria predeterminada
                val dateToBlock = Instant.ofEpochMilli(utcDateInMills).atZone(ZoneId.of("UTC"))
                    .toLocalDate()
                dateToBlock >= currentDate
            })
        }
    }
    Icon(Icons.Default.DateRange, contentDescription = "Calendar Icon", modifier = Modifier.pointerInput(Unit){
        detectTapGestures(onTap = {
            openDialog.value = true
        })
    })
}

@Composable
fun daysState(alarmViewModel: AlarmViewModel) {
    Text(text = "" + alarmViewModel.selectedDaysFormat.observeAsState().value)
}

