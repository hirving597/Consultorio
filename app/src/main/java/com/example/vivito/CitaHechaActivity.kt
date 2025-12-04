package com.example.vivito

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class CitaHechaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita_hecha)

        // Redirigir automáticamente a los 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, CitasActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
