package com.udb.alarmapp.presentation.screens.medicinesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udb.alarmapp.data.local.model.MedicineModel
import com.udb.alarmapp.domain.usecases.AddMedicineUseCase
import com.udb.alarmapp.domain.usecases.DeleteMedicineUseCase
import com.udb.alarmapp.domain.usecases.GetMedicinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicinesViewModel @Inject constructor(
    private val addMedicineUseCase: AddMedicineUseCase,
    private val deleteMedicineUseCase: DeleteMedicineUseCase,
    getMedicinesUseCase: GetMedicinesUseCase,
) : ViewModel() {
    private val _medicines = MutableStateFlow<List<MedicineModel>>(emptyList())
    val medicines: StateFlow<List<MedicineModel>>
        get() = _medicines

    init {
        viewModelScope.launch {
            getMedicinesUseCase().collect {
                _medicines.value = it
            }
        }
    }

    fun onSubmitMedicinesForm(medicine: String, doctorName: String, stock: String) {
        viewModelScope.launch {
            addMedicineUseCase(
                MedicineModel(
                    medicine = medicine,
                    doctorName = doctorName,
                    stock = stock
                )
            )
        }
    }

    fun onDeleteMedicine(medicineModel: MedicineModel) {
        viewModelScope.launch {
            deleteMedicineUseCase(medicineModel)
        }
    }

}