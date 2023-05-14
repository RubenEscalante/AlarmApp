package com.udb.alarmapp.presentation.screens.homescreen

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.data.local.model.DataListQueryModel
import com.udb.alarmapp.data.local.model.RecordModel
import com.udb.alarmapp.domain.usecases.AddRecordUseCase
import com.udb.alarmapp.domain.usecases.DeleteAlarmUseCase
import com.udb.alarmapp.domain.usecases.GetAlarmsUseCase
import com.udb.alarmapp.domain.usecases.GetDateListUseCase
import com.udb.alarmapp.domain.usecases.UpdateActiveUseCase
import com.udb.alarmapp.presentation.viewmodels.NotificationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val updateActiveUseCase: UpdateActiveUseCase,
    private val addRecordUseCase: AddRecordUseCase,
    application: Application,
    getDateListUseCase: GetDateListUseCase
) : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val notificationUtils = NotificationUtils(context)
    private val _alarms = MutableStateFlow<List<CompleteAlarmModel>>(emptyList())
    val alarms: StateFlow<List<CompleteAlarmModel>>
        get() = _alarms
    private val _dateList = MutableStateFlow<List<DataListQueryModel>>(emptyList())
    val dateList: StateFlow<List<DataListQueryModel>>
        get() = _dateList

    init {
        viewModelScope.launch {
            getAlarmsUseCase().collect {
                _alarms.value = it
            }
        }
        viewModelScope.launch {
            getDateListUseCase().collect {
                _dateList.value = it
            }
        }
//        scheduleNotification(5000L,0, _dateList.value.toString(), "Primer noti")
//        scheduleNotification(10000L,1, "asdsadsad", "Segunda noti")
    }

    fun deleteAlarm(alarmId: String) {
        viewModelScope.launch {
            deleteAlarmUseCase(alarmId = alarmId)
        }
    }

    fun scheduleNotification(delay: Long, id: Int, title: String, message: String) {
        val notification = notificationUtils.buildNotification(
            title,
            message,
            id = id
        )
        notificationUtils.scheduleNotification(notification, id, delay)
    }

    fun addRecord(record: RecordModel) {
        viewModelScope.launch {
            addRecordUseCase(record)
        }
    }

    fun updateActiveAlarm(alarmId: String, isSwitched: Boolean) {
        viewModelScope.launch {
            updateActiveUseCase(alarmId, isSwitched)
        }
    }
}