package com.example.vivito

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vivito.database.AppDatabaseC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val tvUsers = findViewById<TextView>(R.id.tvUsers)

        val db = AppDatabaseC .getDatabase(this)
        val userDao = db.userDao()

        // Cargar usuarios en un coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val users = userDao.getAllUsers() // Función que devuelve todos los usuarios
            withContext(Dispatchers.Main) {
                if (users.isEmpty()) {
                    tvUsers.text = "No hay usuarios registrados"
                } else {
                    val sb = StringBuilder()
                    users.forEach { user ->
                        sb.append("Nombre: ${user.nombreCompleto}\n")
                        sb.append("Email: ${user.correo}\n")
                        sb.append("Teléfono: ${user.telefono}\n")
                        sb.append("Edad: ${user.edad}\n\n")
                    }
                    tvUsers.text = sb.toString()
                }
            }
        }
    }
}
