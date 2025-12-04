package com.example.vivito.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val paciente: String,
    val especialidad: String,
    val fecha: String,    // Guardamos como String, ej: "2025-12-05"
    val hora: String      // Guardamos como String, ej: "14:30"
)
