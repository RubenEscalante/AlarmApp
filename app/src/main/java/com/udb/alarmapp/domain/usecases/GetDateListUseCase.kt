package com.udb.alarmapp.domain.usecases

import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.data.local.model.DataListQueryModel
import com.udb.alarmapp.data.local.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDateListUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    operator fun invoke(): Flow<List<DataListQueryModel>> = alarmRepository.dateList
}