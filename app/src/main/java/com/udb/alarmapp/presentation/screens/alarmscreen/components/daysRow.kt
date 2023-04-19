package com.udb.alarmapp.presentation.screens.alarmscreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun daysRow() {
    val days = listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")
    val diasSeleccionados = remember { mutableStateListOf<String>() }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(days) { day ->
            val textColor by animateColorAsState(
                if (day in diasSeleccionados) Color.White else Color(
                    0xff312b63
                )
            )
            val backGroundColor by animateColorAsState(
                if (day in diasSeleccionados) Color(
                    0xffb178ff
                ) else Color.White
            )
            dayBox(day = day,
                backgroundColor = backGroundColor,
                textColor = textColor,
                onClick = {
                    if (day in diasSeleccionados) {
                        diasSeleccionados.remove(day)
                    } else {
                        diasSeleccionados.add(day)
                    }
                })
        }
    }
}