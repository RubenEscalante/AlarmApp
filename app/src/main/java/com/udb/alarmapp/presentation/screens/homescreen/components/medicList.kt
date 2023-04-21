package com.udb.alarmapp.presentation.screens.homescreen.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.udb.alarmapp.data.local.model.MedicineModel

@Composable
fun medicList(medicines: List<MedicineModel>) {
    LazyColumn(modifier = Modifier.height(100.dp)){
        items(medicines){
            medicCard(it)
        }
    }
}