package com.udb.alarmapp.presentation.screens.homescreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun medicCard(){
    Text(
        text = "Paracetamol 500mg",
        style = MaterialTheme.typography.body1,
        color = Color(0xff312b63),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 0.dp, bottom = 6.dp)
    )
}