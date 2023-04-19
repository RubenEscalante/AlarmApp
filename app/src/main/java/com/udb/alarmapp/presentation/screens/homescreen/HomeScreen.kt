package com.udb.alarmapp.presentation.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.udb.alarmapp.presentation.screens.homescreen.components.alarmCard

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, onNavigateToAlarm:() -> Unit) {
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
                text = "Smart Alarm",
                fontSize = 30.sp,
                fontWeight = FontWeight(300),
                color = Color(0xff312b63)
            )
        }
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
                    items(5) {
                        alarmCard(homeViewModel)
                    }
                }
            }
        }
    }
}