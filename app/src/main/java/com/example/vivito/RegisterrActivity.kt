package com.example.vivito

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vivito.database.AppDatabaseC
import com.example.vivito.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterrActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtFullName = findViewById<EditText>(R.id.edtFullName)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val edtRepeatPassword = findViewById<EditText>(R.id.edtRepeatPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val db = AppDatabaseC.getDatabase(this)
        val userDao = db.userDao()

        btnRegister.setOnClickListener {
            val fullName = edtFullName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val age = edtAge.text.toString().trim().toIntOrNull() ?: 0
            val password = edtPassword.text.toString().trim()
            val repeatPassword = edtRepeatPassword.text.toString().trim()

            // Validaciones
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                age == 0 || password.isEmpty() || repeatPassword.isEmpty()
            ) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar usuario en Room
            CoroutineScope(Dispatchers.IO).launch {
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser == null) {
                    val user = User(
                        nombreCompleto = fullName,
                        correo = email,
                        contrasena = password,
                        telefono = phone,
                        edad = age
                    )
                    userDao.insertUser(user)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterrActivity, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        finish() // cierra activity y vuelve al login
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterrActivity, "Correo ya registrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
