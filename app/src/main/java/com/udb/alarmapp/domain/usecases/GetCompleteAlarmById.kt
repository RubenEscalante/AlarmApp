package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.data.local.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompleteAlarmById @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    operator fun invoke(alarmId: String): Flow<CompleteAlarmModel> = alarmRepository.getCompleteAlarmById(alarmId)

}