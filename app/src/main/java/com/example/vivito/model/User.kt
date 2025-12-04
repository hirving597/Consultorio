package com.example.vivito.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreCompleto: String,
    val correo: String,
    val contrasena: String,
    val telefono: String,
    val edad: Int
)
