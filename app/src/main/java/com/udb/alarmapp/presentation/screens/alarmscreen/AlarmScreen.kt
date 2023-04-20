package com.udb.alarmapp.presentation.screens.alarmscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.presentation.components.TimeFormat
import com.udb.alarmapp.presentation.components.WheelTimePicker
import com.udb.alarmapp.presentation.screens.alarmscreen.components.daysRow
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesViewModel


@Composable
fun AlarmScreen(medicinesViewModel: MedicinesViewModel, alarmViewModel: AlarmViewModel) {
    val myMedicines = medicinesViewModel.medicines.collectAsState(initial = emptyList())
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
            selectionMedicine(myMedicines)
            aditionalNotes()
            Button(onClick = { alarmViewModel.addAlarm()}) {
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
fun selectionMedicine(myMedicines: State<List<MedicineModel>>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Medicinas")
        LazyColumn {
            items(
                myMedicines.value,
                key = { it.id }) { medicine ->
                medicineCard(medicine)
            }
        }
    }
}

@Composable
fun medicineCard(medicine: MedicineModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = medicine.medicine)
        Checkbox(
            checked = true, onCheckedChange = {}, colors = CheckboxDefaults.colors(
                checkedColor = Color(0xffb178ff),
                uncheckedColor = Color.Transparent
            )
        )
    }

}

@Composable
fun calendar() {
    Icon(Icons.Default.DateRange, contentDescription = "Calendar Icon")
}

@Composable
fun daysState(alarmViewModel: AlarmViewModel) {
    Text(text = "Hoy, " + alarmViewModel.getToday())
}

