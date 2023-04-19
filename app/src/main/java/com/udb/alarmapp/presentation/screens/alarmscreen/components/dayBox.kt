package com.udb.alarmapp.presentation.screens.alarmscreen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun dayBox(day: String, backgroundColor: Color, textColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(60.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(0.dp))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onClick()
                })
            }
            .animateContentSize(),
        backgroundColor = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight(700),
                color = textColor
            )
        }

    }
}