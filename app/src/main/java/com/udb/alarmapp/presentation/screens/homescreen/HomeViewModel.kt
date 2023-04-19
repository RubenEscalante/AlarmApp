package com.udb.alarmapp.presentation.screens.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    private val _switchState = MutableLiveData<Boolean>()
    val switchState: LiveData<Boolean> = _switchState

    fun onswitchStateChange(state: Boolean) {
    _switchState.value = state
    }
}