package com.udb.alarmapp.presentation.screens.homescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udb.alarmapp.data.local.model.RecordModel
import com.udb.alarmapp.presentation.screens.homescreen.components.alarmCard
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    medicinesViewModel: MedicinesViewModel,
    onNavigateToAlarm: () -> Unit,
    onNavigateToMedicines: () -> Unit,
    onNavigateToAlarmUpdate: (String) -> Unit
) {
    val myMedicines = medicinesViewModel.medicines.collectAsState(initial = emptyList())
    val myAlarms = homeViewModel.alarms.collectAsState(initial = emptyList())
    val myDates = homeViewModel.dateList.collectAsState(initial = emptyList())

    myDates.value.mapIndexed { index, it ->
        // Parsea los strings de fecha y hora en un objeto Date
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = dateFormat.parse("${it.date} ${it.hour}")
        // Calcula los milisegundos entre la fecha actual y la fecha futura
        val now = Calendar.getInstance()
        val future = Calendar.getInstance().apply { time = date }
        val diffInMillis = future.timeInMillis - now.timeInMillis
        val actualAlarm = myAlarms.value.first { alarm -> alarm.id == it.alarmid }
        if (now.after(future)) {
//            val actualAlarm = myAlarms.value.first { alarm -> alarm.id == it.alarmid }
//            val record = RecordModel(
//                alarmId = actualAlarm.id,
//                date = it.date,
//                hour = it.hour,
//                medicines = actualAlarm.medicines.joinToString(separator = ",")
//            )
//            homeViewModel.addRecord(record)
            // homeViewModel.deleteAlarm(it.alarmid)
            Log.i("Ruben", "Ya paso")
        } else {
            homeViewModel.scheduleNotification(diffInMillis,
                index,
                "Tomar Medicamentos de las ${it.hour}",
                actualAlarm.medicines.joinToString(separator = ", ") { r -> r.medicine })
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                //  .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(Color.White)
                .padding(10.dp)
        )
        {
            Text(
                text = "MediClock UDB",
                fontSize = 30.sp,
                fontWeight = FontWeight(300),
                color = Color(0xff312b63)
            )
        }
        if (myMedicines.value.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¡Empieza!",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Para poder recibir alarmas, primero debes agregar medicamentos.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { onNavigateToMedicines() },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(text = "Agregar Medicamento")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Tus alarmas",
                        color = Color(0xff27205b),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Añadir",
                        modifier = Modifier.clickable { onNavigateToAlarm() })
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    LazyColumn {
                        items(myAlarms.value, key = { it.id }) {
                            alarmCard(it, homeViewModel, onNavigateToAlarmUpdate)
                        }
                    }
                }
            }
        }

    }
}