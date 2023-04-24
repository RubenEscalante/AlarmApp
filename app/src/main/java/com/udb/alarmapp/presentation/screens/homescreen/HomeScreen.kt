package com.udb.alarmapp.presentation.screens.homescreen

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
import com.udb.alarmapp.presentation.screens.homescreen.components.alarmCard
import com.udb.alarmapp.presentation.screens.medicinesScreen.MedicinesViewModel

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
                            alarmCard(it, homeViewModel,onNavigateToAlarmUpdate)
                        }
                    }
                }
            }
        }

    }
}