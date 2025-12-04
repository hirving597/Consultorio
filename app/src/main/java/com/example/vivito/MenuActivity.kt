package com.example.vivito

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.vivito.util.PreferenceHelper
import com.example.vivito.util.PreferenceHelper.set

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            goRegistrar()
        }

        val btnMisCitas = findViewById<Button>(R.id.btnMisCitas)
        btnMisCitas.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            startActivity(intent)
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            clearSessionPreference()
            goToLogin()
        }
    }

    private fun goRegistrar() {
        val i = Intent(this, CitasActivity::class.java)
        startActivity(i)
    }

    private fun goToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearSessionPreference() {
        val preference = PreferenceHelper.defaultPrefs(this)
        preference["session"] = false
    }
}
