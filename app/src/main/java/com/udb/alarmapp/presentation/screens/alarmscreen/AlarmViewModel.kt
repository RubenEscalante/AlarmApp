package com.udb.alarmapp.presentation.screens.alarmscreen

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udb.alarmapp.data.local.model.AlarmDateModel
import com.udb.alarmapp.data.local.model.AlarmMedicineModel
import com.udb.alarmapp.data.local.model.AlarmModel
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.domain.usecases.AddAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase
) : ViewModel() {


    private val _selectedMedicines = MutableLiveData<List<MedicineModel>>(emptyList())
    val selectedMedicines: LiveData<List<MedicineModel>>
        get() = _selectedMedicines

    private var _dates = mutableListOf<String>()
    private var _hour: String = ""
    private var _ampm: String = ""
    private var _sunmoon: String = ""
    private var _days = mutableListOf<String>()

    fun getToday(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return today.format(formatter)
    }

    fun changeDaysSelection(diasSeleccionados: SnapshotStateList<String>) {
        _days = orderDays(diasSeleccionados)
        if (_days.isNotEmpty()) {
            _dates = getDatesFromSelectedDays(_days).toMutableList()
        } else {
            _days.add(getToday())
        }
    }

    private fun getDatesFromSelectedDays(selectedDays: MutableList<String>): List<String> {

        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        val dates = mutableListOf<String>()
        for (dayOfWeek in selectedDays) {
            val dayOfWeekNumber = when (dayOfWeek) {
                "Dom" -> Calendar.SUNDAY
                "Lun" -> Calendar.MONDAY
                "Mar" -> Calendar.TUESDAY
                "Mie" -> Calendar.WEDNESDAY
                "Jue" -> Calendar.THURSDAY
                "Vie" -> Calendar.FRIDAY
                "Sab" -> Calendar.SATURDAY
                else -> throw IllegalArgumentException("Día de la semana inválido: $dayOfWeek")
            }
            calendar.time = currentDate
            while (calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeekNumber) {
                calendar.add(Calendar.DAY_OF_WEEK, 1)
            }
            dates.add(SimpleDateFormat("dd/MM/yyyy").format(calendar.time))
        }
        return dates
    }

    fun addHour(snappedTime: String) {
        _hour = snappedTime
        _ampm = getAmPm(snappedTime)
        _sunmoon =  getSunOrMoon(snappedTime)
    }

    private fun orderDays(diasSeleccionados: SnapshotStateList<String>): MutableList<String> {
        if (diasSeleccionados.isEmpty()) {
            return mutableListOf()
        }
        val diasOrden = listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")
        return diasSeleccionados.joinToString(",")
            .split(",")
            .sortedBy { diasOrden.indexOf(it) }
            .toMutableList()
    }

    fun getAmPm(hourString: String): String {
        val time = LocalTime.parse(hourString, DateTimeFormatter.ofPattern("HH:mm"))
        val hour = time.hour
        return if (hour < 12) "AM" else "PM"
    }
    fun getSunOrMoon(hourString: String): String {
        val time = LocalTime.parse(hourString, DateTimeFormatter.ofPattern("HH:mm"))
        val hour = time.hour
        return if (hour in 5..17) "sun" else "moon"
    }

    fun addAlarm() {
        val alarm = AlarmModel(
            hour = _hour,
            ampm = _ampm,
            sunmoon = _sunmoon,
            days = _days.toString()
        )
        val alarmDates = _dates.map {
            AlarmDateModel(alarmid = alarm.id, date = it)
        }.toMutableList()
        val medicineList = _selectedMedicines.value?.map {
            AlarmMedicineModel(
                alarmId = alarm.id,
                medicinesId = it.id
            )
        }
        clearScreenState()
        viewModelScope.launch {
            if (medicineList != null) {
                addAlarmUseCase(
                    alarm,
                    alarmDates,
                    medicineList
                )
            }
        }
    }

    fun updateSelectedMedicines(isChecked: Boolean, medicine: MedicineModel) {
        val selectedAlarms = _selectedMedicines.value?.toMutableList() ?: mutableListOf()
        if (isChecked) {
            selectedAlarms.add(medicine)
        } else {
            selectedAlarms.remove(medicine)
        }
        _selectedMedicines.value = selectedAlarms
    }

    fun clearScreenState() {
        _selectedMedicines.value = emptyList()
    }

}