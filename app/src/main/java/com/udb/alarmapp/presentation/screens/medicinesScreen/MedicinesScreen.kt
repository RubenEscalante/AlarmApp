package com.udb.alarmapp.presentation.screens.medicinesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.udb.alarmapp.data.local.model.MedicineModel

@Composable
fun MedicinesScreen(medicinesViewModel: MedicinesViewModel) {
    val myMedicines = medicinesViewModel.medicines.collectAsState(initial = emptyList())
    var dialogState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tus medicinas",
                color = Color(0xff27205b),
                fontSize = 18.sp,
                fontWeight = FontWeight(700)
            )
            Icon(
                Icons.Default.Add,
                contentDescription = "Añadir",
                modifier = Modifier.clickable { dialogState = true })
            medicineDialog(
                state = dialogState,
                onDismiss = { dialogState = !dialogState },
                medicinesViewModel = medicinesViewModel
            )
        }
        LazyColumn {
            items(
                myMedicines.value,
                key = { it.id }) { medicine ->
                cardMedicine(medicineModel = medicine) { medicinesViewModel.onDeleteMedicine(it) }
            }
        }
    }
}

@Composable
fun cardMedicine(medicineModel: MedicineModel, onDelete: (MedicineModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {

                })
            }
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.width(130.dp), verticalArrangement = Arrangement.Center) {
                Text(
                    text = medicineModel.medicine, style = MaterialTheme.typography.h6,
                    color = Color(0xff312b63), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                if (medicineModel.doctorName != "") {
                    Text(
                        text = medicineModel.doctorName, style = MaterialTheme.typography.subtitle2,
                        color = Color(0xffcac9d7)
                    )
                }
            }
            if (medicineModel.stock != "") {
                Column() {
                    Text(text = "Existencias")
                    Text(text = medicineModel.stock)
                }
            }
            Icon(Icons.Default.Delete, contentDescription = "Delete Icon",
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        onDelete(medicineModel)
                    })
                })
        }
    }
}

@Composable
fun medicineDialog(state: Boolean, onDismiss: () -> Unit, medicinesViewModel: MedicinesViewModel) {
    var enableButton by remember { mutableStateOf(false) }
    var medicineName by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    if (state) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                //shape = MaterialTheme.shapes.medium,
                shape = RoundedCornerShape(10.dp),
                // modifier = modifier.size(280.dp, 240.dp)
                modifier = Modifier.padding(8.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .background(Color.White)
                ) {

                    Text(
                        text = "Agregar Medicamento",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        value = medicineName,
                        onValueChange = { medicineName = it }, modifier = Modifier.padding(8.dp),
                        label = { Text("Nombre") }
                    )
                    OutlinedTextField(
                        value = stock,
                        onValueChange = { stock = it }, modifier = Modifier.padding(8.dp),
                        label = { Text("Existencias") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = doctorName,
                        onValueChange = { doctorName = it }, modifier = Modifier.padding(8.dp),
                        label = { Text("Medico") }
                    )


                    Row {
                        OutlinedButton(
                            onClick = { onDismiss() },
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Cancelar")
                        }

                        if (medicineName != "") enableButton = true
                        Button(
                            enabled = enableButton,
                            onClick = {
                                medicinesViewModel.onSubmitMedicinesForm(
                                    medicine = medicineName,
                                    doctorName = doctorName,
                                    stock = stock
                                )
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Agregar")
                        }
                    }
                }
            }
        }
    }
}

