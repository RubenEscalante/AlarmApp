package com.udb.alarmapp.presentation.screens.medicinesScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.data.local.room.state.MedicinesState
import kotlinx.coroutines.flow.collect

@Composable
fun MedicinesScreen(medicinesViewModel: MedicinesViewModel) {
    val myMedicines = medicinesViewModel.medicines.collectAsState(initial = emptyList())
    var medicineName by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = medicineName,
            onValueChange = { medicineName = it },
            label = { Text(text = "Nombre") })
        TextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text(text = "Existente") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = doctorName,
            onValueChange = { doctorName = it },
            label = { Text(text = "Medico") }
        )
        Button(onClick = {
            medicinesViewModel.onSubmitMedicinesForm(
                medicine = medicineName,
                stock = stock,
                doctorName = doctorName
            )
        }) {
            Text(text = "Guardar")
        }
        LazyColumn {
            items(
                myMedicines.value,
                key = { it.id }) { medicine ->
                cardMedicine(medicineModel = medicine)

            }
        }
    }
}

@Composable
fun cardMedicine(medicineModel: MedicineModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), elevation = 8.dp
    ) {
        Row(Modifier) {
            Text(text = medicineModel.medicine)
            Text(text = medicineModel.stock)
        }
    }
}

