package com.example.vivito.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vivito.model.Cita

@Dao
interface CitaDao {
    @Insert
    suspend fun insertCita(cita: Cita)

    @Query("SELECT * FROM citas")
    suspend fun getAllCitas(): List<Cita>
}
