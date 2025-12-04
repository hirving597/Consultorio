package com.example.vivito

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vivito.database.AppDatabaseC
import com.example.vivito.util.PreferenceHelper
import com.example.vivito.util.PreferenceHelper.get
import com.example.vivito.util.PreferenceHelper.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtEmail = findViewById<EditText>(R.id.edtEmailLogin)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btn_go_to_menu)
        val tvGoRegister = findViewById<TextView>(R.id.tv_go_to_register)

        val preference = PreferenceHelper.defaultPrefs(this)
        if (preference["session", false]) goToMenu()

        tvGoRegister.setOnClickListener { goToRegister() }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = AppDatabaseC.getDatabase(this)
            val userDao = db.userDao()

            CoroutineScope(Dispatchers.IO).launch {
                val user = userDao.login(email, password)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_SHORT).show()
                        createSessionPreference()
                        goToMenu()
                    } else {
                        Toast.makeText(this@MainActivity, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterrActivity::class.java)
        startActivity(i)
    }

    private fun goToMenu() {
        val i = Intent(this, MenuActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun createSessionPreference() {
        val preference = PreferenceHelper.defaultPrefs(this)
        preference["session"] = true
    }
}
