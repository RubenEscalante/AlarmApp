package com.udb.alarmapp.presentation.screens.homescreen.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun medicList(){
    LazyColumn(modifier = Modifier.fillMaxHeight()){
        items(4){
            medicCard()
        }
    }
}