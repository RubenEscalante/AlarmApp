package com.udb.alarmapp.data.local.room.state

import com.udb.alarmapp.data.local.model.MedicineModel

sealed interface MedicinesState {
    object Loading : MedicinesState
    data class Error(val throwable: Throwable) : MedicinesState
    data class Success(val medicines: List<MedicineModel>) : MedicinesState
}