package com.udb.alarmapp.presentation.screens.alarmscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.udb.alarmapp.presentation.components.TimeFormat
import com.udb.alarmapp.presentation.components.WheelTimePicker
import com.udb.alarmapp.presentation.screens.alarmscreen.components.daysRow
import kotlin.math.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmScreen() {
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
                Log.i(
                    "Ruben",
                    snappedTime.toString()
                )
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
                daysState()
                calendar()
            }
            daysRow()
            selectionMedicine()
            aditionalNotes()
        }

    }
}

@Composable
fun aditionalNotes() {
    Text(text = "Nota Adicional")
}

@Composable
fun selectionMedicine() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Medicinas")
        Column {
            medicineCard()
            medicineCard()
            medicineCard()
        }
    }
}

@Composable
fun medicineCard() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Paracetamol")
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
fun daysState() {
    Text(text = "Diariamente")
}

