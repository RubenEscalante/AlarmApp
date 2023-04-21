package com.udb.alarmapp.presentation.screens.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.domain.usecases.DeleteAlarmUseCase
import com.udb.alarmapp.domain.usecases.GetAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : ViewModel() {

    private val _alarms = MutableStateFlow<List<CompleteAlarmModel>>(emptyList())
    val alarms: StateFlow<List<CompleteAlarmModel>>
        get() = _alarms

    init {
        viewModelScope.launch {
            getAlarmsUseCase().collect {
                _alarms.value = it
            }
        }
    }

    fun deleteAlarm(alarmId: String) {
        viewModelScope.launch {
            deleteAlarmUseCase(alarmId = alarmId)
        }

    }
}