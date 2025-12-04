package com.example.vivito

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CitasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_citas)

        val btnDent = findViewById<Button>(R.id.btnDent)
        btnDent.setOnClickListener {
            val intent = Intent(this, DatosPCitaActivity::class.java)
            intent.putExtra("especialidad", "Dentista")
            startActivity(intent)
        }

        val btnPedia = findViewById<Button>(R.id.btnPedia)
        btnPedia.setOnClickListener {
            val i = Intent(this, DatosPCitaActivity::class.java)
            i.putExtra("especialidad", "Pediatría")
            startActivity(i)
        }

        val btnCardio = findViewById<Button>(R.id.btnCardio)
        btnCardio.setOnClickListener {
            val i = Intent(this, DatosPCitaActivity::class.java)
            i.putExtra("especialidad", "Cardiología")
            startActivity(i)
        }
        val btnPsiqui = findViewById<Button>(R.id.btnPsiqui)
        btnPsiqui.setOnClickListener {
            val i = Intent(this, DatosPCitaActivity::class.java)
            i.putExtra("especialidad", "Psiquiatría")
            startActivity(i)
        }

        val btnFisio = findViewById<Button>(R.id.btnFisio)
        btnFisio.setOnClickListener {
            val i = Intent(this, DatosPCitaActivity::class.java)
            i.putExtra("especialidad", "Fisioterapia")
            startActivity(i)
        }
        val btnGine = findViewById<Button>(R.id.btnGine)
        btnGine.setOnClickListener {
            val i = Intent(this, DatosPCitaActivity::class.java)
            i.putExtra("especialidad", "Ginecología")
            startActivity(i)
        }

        val btnInicio = findViewById<Button>(R.id.btninicio)
        btnInicio.setOnClickListener {
            goInicio()
        }
    }

    private fun goInicio() {
        val i = Intent(this, MenuActivity::class.java)
        startActivity(i)
        finish() // <- opcional pero RECOMENDADO
    }
}
