package com.example.vivito.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vivito.model.User

@Dao
interface UserDao {

    // Inserta un nuevo usuario en la base de datos
    @Insert
    suspend fun insertUser(user: User)

    // Consulta un usuario por correo y contraseña (para login)
    @Query("SELECT * FROM users WHERE correo = :email AND contrasena = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    // Consulta un usuario por correo (para verificar si ya existe)
    @Query("SELECT * FROM users WHERE correo = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Devuelve todos los usuarios registrados
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}



