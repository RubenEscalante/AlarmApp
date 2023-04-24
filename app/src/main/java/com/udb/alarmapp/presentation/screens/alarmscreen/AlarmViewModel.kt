package com.udb.alarmapp.presentation.screens.alarmscreen


import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udb.alarmapp.data.local.model.AlarmDateModel
import com.udb.alarmapp.data.local.model.AlarmMedicineModel
import com.udb.alarmapp.data.local.model.AlarmModel
import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.domain.usecases.AddAlarmUseCase
import com.udb.alarmapp.domain.usecases.GetCompleteAlarmById
import com.udb.alarmapp.domain.usecases.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase,
    private val getCompleteAlarmById: GetCompleteAlarmById,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {
    private val _onUpdate = MutableStateFlow<Boolean>(false)
    val onUpdate: StateFlow<Boolean>
        get() = _onUpdate

    private val _onUpdateAlarm = MutableStateFlow<CompleteAlarmModel?>(null)
    val onUpdateAlarm: StateFlow<CompleteAlarmModel?>
        get() = _onUpdateAlarm

    fun setUpdateAlarm(alarmId: String?) {
        if (alarmId != null) {
            _onUpdate.value = true
            getCompleteAlarmById(alarmId = alarmId).onEach { medi ->
                _selectedDaysFormat.value = medi.days
                _selectedDays.value = onUpdateChangeDayBox(medi.days)
                _hour.value = returnLocalTime(medi.hour)
                _selectedMedicines.value = medi.medicines.map {
                    it.id
                }
            }.launchIn(viewModelScope)
        } else {
            _hour.value = returnStarTime()
            _onUpdate.value = false
        }

    }

    fun onUpdateChangeDayBox(days: String): List<String> {
        var state: List<String> = emptyList()
        if (days == "Diariamente") {
            state = listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")
            diasSeleccionados.addAll(state)
        } else if (days == "Hoy") {
            state = emptyList()
        } else {
            state = days.split(",").map { it.trim() }
            diasSeleccionados.addAll(state)
        }
        return state
    }

    var diasSeleccionados = mutableStateListOf<String>()

    private val _selectedMedicines = MutableLiveData<List<String>>()
    val selectedMedicines: LiveData<List<String>>
        get() = _selectedMedicines

    //Nos ayuda a marcar los dias en la vista
    private val _selectedDays = MutableStateFlow<List<String>>(emptyList())
    val selectedDays: StateFlow<List<String>>
        get() = _selectedDays

    //Para guardar en la DB el label, Hoy si no hay dia seleccionado, Diariamente si todos o los dias
    //Tambien la fecha si se elije una en el calendar.
    private var _selectedDaysFormat = MutableStateFlow(assingDays())
    val selectedDaysFormat: StateFlow<String>
        get() = _selectedDaysFormat

    //Para manejar la hora
    private var _hour = MutableLiveData<LocalTime>()
    val hour: LiveData<LocalTime>
        get() = _hour

    private var _dates = mutableListOf(getToday())
    private var _ampm: String = ""
    private var _sunmoon: String = ""

    fun getToday(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return today.format(formatter)
    }

    fun changeDaysSelection(diasSeleccionados: SnapshotStateList<String>) {
        _selectedDays.value = orderDays(diasSeleccionados)
        if ((_selectedDays.value as MutableList<String>).isNotEmpty()) {
            _dates =
                getDatesFromSelectedDays(_selectedDays.value as MutableList<String>) as MutableList<String>
        } else {
            _selectedDays.value = listOf("")
        }
        getSelectedDaysFormat()
    }

    private fun getSelectedDaysFormat() {
        if (_selectedDays.value?.isEmpty() == true || (_selectedDays.value as MutableList<String>).contains(
                ""
            )
        ) {
            _selectedDaysFormat.value = "Hoy"
        } else
            if ((_selectedDays.value as MutableList<String>).size == 7) {
                _selectedDaysFormat.value = "Diariamente"
            } else {
                _selectedDaysFormat.value = returnStateSelectedDaysFormat()
            }
    }

    fun assingDays(): String {
        return "Hoy"
    }

    fun returnStarTime(): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
//        if (_hour.value.isEmpty()) {
        val localTime = LocalTime.now()
        val formattedTime = localTime.format(formatter)
//        }
        return LocalTime.parse(formattedTime, formatter)
    }

    fun returnLocalTime(time: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
//        if (_hour.value.isEmpty()) {
//        val localTime = LocalTime.now()
//        val formattedTime = localTime.format(formatter)
//        }
        return LocalTime.parse(time, formatter)
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
                else -> null
//                    throw IllegalArgumentException("Día de la semana inválido: $dayOfWeek")
            }
            calendar.time = currentDate
            while (calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeekNumber) {
                calendar.add(Calendar.DAY_OF_WEEK, 1)
            }
            dates.add(SimpleDateFormat("dd/MM/yyyy").format(calendar.time))
        }
        return dates
    }

    fun addHour(snappedTime: LocalTime) {
        _hour.value = snappedTime
        _ampm = getAmPm(snappedTime.toString())
        _sunmoon = getSunOrMoon(snappedTime.toString())
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

    private fun returnStateSelectedDaysFormat(): String {
        return _selectedDays.value.toString().replace("[", "").replace("]", "")
    }

    fun addAlarm(onUpdate: Boolean, alarmId: String?) {
        if ((_selectedDays.value.isNullOrEmpty() || _selectedDays.value!!.contains("")) && !enableCalendar) {
            _dates = listOf(getToday()) as MutableList<String>
        }
        val alarm = AlarmModel(
            hour = _hour.value.toString(),
            ampm = _ampm,
            sunmoon = _sunmoon,
            days = _selectedDaysFormat.value.toString()
        )
        val alarmDates = _dates.map {
            AlarmDateModel(alarmid = alarm.id, date = it)
        }.toMutableList()
        val medicineList = _selectedMedicines.value?.map {
            AlarmMedicineModel(
                alarmId = alarm.id,
                medicinesId = it
            )
        }

        if (!onUpdate) {
            viewModelScope.launch {
                if (medicineList != null) {
                    addAlarmUseCase(
                        alarm,
                        alarmDates,
                        medicineList
                    )
                }
            }

        } else {
            val alarmUpdateModel = alarmId?.let {
                AlarmModel(
                    id = it,
                    hour = _hour.value.toString(),
                    ampm = _ampm,
                    sunmoon = _sunmoon,
                    days = _selectedDaysFormat.value.toString()
                )
            }
            val alarmDatesUpdate = _dates.mapNotNull {
                alarmId?.let { id -> AlarmDateModel(alarmid = id, date = it) }
            }.toMutableList()
            val medicineListUpdate = _selectedMedicines.value?.mapNotNull {
                if (alarmId != null) {
                    AlarmMedicineModel(
                        alarmId = alarmId,
                        medicinesId = it
                    )
                } else {
                    null
                }
            }
            viewModelScope.launch {
                if (medicineList != null) {
                    if (alarmId != null) {
                        if (alarmUpdateModel != null) {
                            if (medicineListUpdate != null) {
                                updateAlarmUseCase(
                                    alarmId,
                                    alarmUpdateModel,
                                    alarmDatesUpdate,
                                    medicineListUpdate
                                )
                            }
                        }
                    }
                }
            }
        }
        clearScreenState()
        enableCalendar = false
    }

    fun updateSelectedMedicines(isChecked: Boolean, medicine: MedicineModel) {
        val selectedAlarms = _selectedMedicines.value?.toMutableList() ?: mutableListOf()
        if (isChecked) {
            selectedAlarms.add(medicine.id)
        } else {
            selectedAlarms.remove(medicine.id)
        }
        _selectedMedicines.value = selectedAlarms
    }

    fun clearScreenState() {
        _selectedMedicines.value = emptyList()
        _selectedDays.value = emptyList()
        diasSeleccionados.clear()
        _dates = emptyList<String>().toMutableList()
        _selectedDaysFormat.value = assingDays()
        _hour.value = null
    }

    fun selectCalendarDate(selectedDateMillis: Long) {
        val localDate = LocalDate.ofEpochDay(selectedDateMillis / 86400000)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = localDate.format(formatter)
        _selectedDaysFormat.value = formattedDate
        _dates = listOf(formattedDate) as MutableList<String>
        enableCalendar = true
        _selectedDays.value = emptyList()
        diasSeleccionados.clear()

    }

    var enableCalendar: Boolean = false
}