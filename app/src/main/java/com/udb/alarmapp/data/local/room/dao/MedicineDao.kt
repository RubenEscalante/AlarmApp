package com.udb.alarmapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.udb.alarmapp.data.local.room.entities.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Query("SELECT * FROM MedicineEntity")
    fun getMedicines(): Flow<List<MedicineEntity>>
    @Insert
    suspend fun addMedicine(item: MedicineEntity)
    @Delete
    suspend fun deleteMedicine(item: MedicineEntity)
}